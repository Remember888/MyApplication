package com.example.musicmanager.service;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.musicmanager.constant.IURL;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public class MyService extends Service{
    private Thread progressThread;
    private MediaPlayer player;
    private MyMusicReceiver myMusicReceiver;
    private boolean ispause = false;
    private static int seekTo = 0;
    private static String mmusicPath;
    boolean progressFlag = true;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("TAG:error",
                        "播放器出错:what" + what +
                                "extra:" + extra);
                return true;
            }
        });
        registerMusicReceive();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                Intent intent = new Intent(IURL.PLAYNEXT_ACTION);
                sendBroadcast(intent);
            }
        });

        //时刻监视MediaPlayer的运行，并传递给SeekBar
        progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressFlag) {
                        if (player.isPlaying()) {
                            int current = player.getCurrentPosition();
                            int duration = player.getDuration();
                            Intent intent = new Intent(IURL.UPSEEKBAR_ACTION);
                            intent.putExtra("current",current);
                            intent.putExtra("duration",duration);
                            sendBroadcast(intent);
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        progressThread.start();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myMusicReceiver);
        progressFlag = false;
    }

    private void registerMusicReceive() {
        myMusicReceiver = new MyMusicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IURL.PLAYMUSIC_ACTION);
        intentFilter.addAction(IURL.PAUSEMUSIC_ACTION);
        intentFilter.addAction(IURL.UPPLAYER_ACTION);
        intentFilter.addAction(IURL.PLAYNEXTWIDGET_ACTION);
        registerReceiver(myMusicReceiver, intentFilter);
    }

    private class MyMusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(IURL.PLAYMUSIC_ACTION)) {
                String musicPath = intent.getStringExtra("musicPath");
                play(musicPath);
            } else if (action.equals(IURL.PAUSEMUSIC_ACTION)) {
                String musicPath = intent.getStringExtra("musicPath");
                pause(musicPath);
            } else if (action.equals(IURL.UPPLAYER_ACTION)) {
                int seekProgress = intent.getIntExtra("seekprogress", 0);
                seekTo(seekProgress);
            } else if (action.equals(IURL.PLAYNEXTWIDGET_ACTION)) {
                String musicPath = intent.getStringExtra("musicPath");
                play(musicPath);
            }

        }
    }

    private void seekTo(int seekProgress) {
        seekTo = player.getDuration() * seekProgress / 100;
        player.seekTo(seekTo);
    }

    private void pause(String musicPath) {
        if (player.isPlaying()) {
            ispause = true;
            mmusicPath = musicPath;
            seekTo = player.getCurrentPosition();
            player.pause();

            Intent intent = new Intent();
            intent.setAction(IURL.UPPLAYWIDGET_ACTION);
            sendBroadcast(intent);
        }
    }

    private void play(String musicPath) {

            try {
                if (ispause&&mmusicPath.equals(musicPath)) {
                        ispause = false;
                        player.seekTo(seekTo);
                        player.start();
                        seekTo = 0;

                } else {
                    ispause = false;
                    player.reset();
                    player.setDataSource(musicPath);
                    player.prepareAsync();
                }
                Intent intent = new Intent();
                intent.putExtra("musicPath", musicPath);
                intent.setAction(IURL.UPPAUSEWIDGET_ACTION);
                sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


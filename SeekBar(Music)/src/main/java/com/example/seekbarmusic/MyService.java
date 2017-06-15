package com.example.seekbarmusic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private Notification notification;
    private int currentPosition;
    private NotificationManager manager;
    private final String STOP = "action.STOP";
    private ScheduledExecutorService executorService;
    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newSingleThreadScheduledExecutor();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Mybinder();
    }

    private class Mybinder extends Binder implements IPlayer{

        @Override
        public void playMusic() {
            play();
        }

        @Override
        public void pauseMusic() {
            pause();
        }

        @Override
        public void setCurrentPosition(int currentPosition) {
            MyService.this.currentPosition = currentPosition;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        executorService.shutdown();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = null;
        if (intent != null) {
            action =  intent.getAction();
        }
        if (STOP.equals(action)) {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    private void play() {
        mediaPlayer.reset();
        try{
            mediaPlayer.setDataSource("/storage/emulated/0/Music/AA.mp3");
            mediaPlayer.prepareAsync();
        }catch (Exception e){};
        notificationCreate();
        upDataProgress();
    }
    Runnable upDateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null){
            //1.获得音乐的总时长
            int duration = mediaPlayer.getDuration();
            //2.获得当前音乐的播放位置
            int currentPostion = mediaPlayer.getCurrentPosition();
            //3.将值发送activity端，更新seekbar

            Intent target = new Intent("action.ATY.RECEIVER01");
            target.putExtra("durationKey", duration);
            target.putExtra("currentPostionKey", currentPostion);
            LocalBroadcastManager.getInstance(MyService.this)
                    .sendBroadcast(target);
            }
            }

    };

    private void upDataProgress() {
            executorService.scheduleWithFixedDelay(upDateSeekBar,1,1,TimeUnit.SECONDS);
    }

    private void notificationCreate() {
        PendingIntent pending = PendingIntent.getService(this,100,new Intent(STOP),PendingIntent.FLAG_CANCEL_CURRENT);
        notification = new Notification.Builder(this)
                .setContentIntent(pending)
                .setTicker("音乐启动")
                .setContentTitle("你的配角")
                .setContentText("回音哥")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
        startForeground(1,notification);
    }

    private void pause(){
        mediaPlayer.pause();
        currentPosition = mediaPlayer.getCurrentPosition();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.seekTo(currentPosition);
        mp.start();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        currentPosition = 0;
        play();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        manager.cancel(1);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}

package com.example.startbindmusic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static android.R.attr.name;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public class MyService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer media;
    private int currentprogress;
    private NotificationManager manager;
    private final  String STOP = "action.STOP";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        media = new MediaPlayer();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        media.setOnCompletionListener(this);
        media.setOnPreparedListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = null;
        if (intent != null) {
            name = intent.getAction();
        }
        if (name.equals(STOP)) {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.seekTo(currentprogress);
        mp.start();
    }


    class IBinder extends Binder implements IPlayer{


        @Override
        public void playerMusic() {
            play();
        }

        @Override
        public void pauseMusic() {
            pause();
        }
    }

    private void pause() {
        if (media.isPlaying()) {
            media.pause();
            currentprogress = media.getCurrentPosition();
        }
    }

    private void play() {
        media.reset();
        try {
            media.setDataSource("/storage/emulated/0/Music/AA.mp3");
            media.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        createNotification();
    }

    private void createNotification() {
        PendingIntent pending = PendingIntent.getService(this,100,new Intent(STOP), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("artist")
                .setContentIntent(pending)
                .build();
        manager.notify(1,notification);
        startForeground(1,notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        manager.cancel(1);
        if (media != null) {
            media.release();
            media = null;
        }
    }

}

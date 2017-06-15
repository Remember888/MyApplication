package com.example.musicplays;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    static  String PLAY = "action.PLAY";
    static  String PAUST = "action.PAUST";
    static  String STOP = "action.STOP";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        if (PLAY.equals(action)) {
            mediaPlayer.start();
        } else if (PAUST.equals(action)) {
            mediaPlayer.pause();
        } else if (STOP.equals(action)) {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("/storage/emulated/0/music/df.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

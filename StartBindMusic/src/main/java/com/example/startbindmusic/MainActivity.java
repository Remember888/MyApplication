package com.example.startbindmusic;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private IPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        bindService(new Intent(this,MyService.class), this,Service.BIND_AUTO_CREATE);
    }

    public void onPlay(View v) {
        if (player != null) {
            player.playerMusic();
        }
    }
    public void onPause(View v) {
        if (player != null) {
            player.pauseMusic();
        }
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        player = (IPlayer) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
        unbindService(this);
    }
}

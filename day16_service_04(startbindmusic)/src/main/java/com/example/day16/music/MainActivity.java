package com.example.day16.music;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

    private IPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        bindService(new Intent(this,SongService.class),this, Context.BIND_AUTO_CREATE);
    }
    public void onPlayClick(View view){
        if(player!=null) {
            //播放音乐
            player.playMusic();
        }
    }
    public void onPauseClick(View v){
       //暂停音乐
        if(player!=null){
            player.pauseMusic();
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        player = (IPlayer) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑之前，再起动service，让service对象不要销毁
        startService(new Intent(this,SongService.class));
        //解绑service(此时只会执行service的onUnbind方法)
        unbindService(this);
    }
}

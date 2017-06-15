package com.example.seekbarmusic;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements ServiceConnection, SeekBar.OnSeekBarChangeListener {
    private SeekBar seekBar;
    private LocalBroadcastManager manager;
    private IPlayer player;
    private MyReceiver receiver;
    private boolean isTracking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekId);
        seekBar.setOnSeekBarChangeListener(this);
        ServiceConnection conn = this;
        Intent intent = new Intent(this,MyService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        //接受本地广播
        receiver = new MyReceiver();
        IntentFilter intentfiler = new IntentFilter();
        intentfiler.addAction("action.ATY.RECEIVER01");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentfiler);

    }

    public void onStart(View view) {
        if (player != null) {
            player.playMusic();
        }
    }

    public void onPause(View view) {
        if (player != null) {
            player.pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(this,MyService.class));
        unbindService(this);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
            player = (IPlayer) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        player.pauseMusic();
        isTracking = false;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
            int current = seekBar.getProgress();
            player.setCurrentPosition(current);
            isTracking = true;
            player.playMusic();
    }

    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isTracking) {
                int duration = intent.getIntExtra("durationKey", 0);
                int currentPostion = intent.getIntExtra("currentPostionKey", 0);
                seekBar.setMax(duration);//将总时长设置为进度的最大值
                seekBar.setProgress(currentPostion);//设置当前进度为播放的当前时长
            }
        }
    }
}

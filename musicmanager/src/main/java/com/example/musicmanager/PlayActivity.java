package com.example.musicmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicmanager.constant.IURL;
import com.example.musicmanager.entity.Music;
import com.example.musicmanager.manager.DownloadManager;
import com.example.musicmanager.manager.MyImageLocaler;
import com.example.musicmanager.view.DiskView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_return_play;
    private ImageButton ib_list_play;
    private ImageButton ib_previous_play;
    private ImageButton ib_start_play;
    private ImageButton ib_next_play;
    private ImageView iv_download_play;
    private ImageView iv_favo_play;
    private TextView tv_totaltime_play;
    private TextView tv_underwaytime_play;
    private TextView tv_title;
    private SeekBar seekBar;
    private DiskView diskView;
    private String path;
    private int currentPosition;
    private Music music;
    private List<Music> list;
    private boolean isPause = false;
    private MyBroadcastReceiver receiver;
    private int seekprogress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initUI();
        initDatas();
        MyOnSeekBarChangeListener listener = new MyOnSeekBarChangeListener();
        seekBar.setOnSeekBarChangeListener(listener);
    }


    //获得Intent传递过来的数据
    private void initDatas() {
        Intent intent = getIntent();
        list = (ArrayList<Music>)intent.getSerializableExtra("list");
        currentPosition = intent.getIntExtra("position",0);
        music = list.get(currentPosition);
    }

    //初始化UI控件
    private void initUI() {
        ib_return_play = (ImageButton) findViewById(R.id.ib_return_play);
        ib_list_play = (ImageButton) findViewById(R.id.ib_list_play);
        ib_previous_play = (ImageButton) findViewById(R.id.ib_previous_play);
        ib_start_play = (ImageButton) findViewById(R.id.ib_start_play);
        ib_next_play = (ImageButton) findViewById(R.id.ib_next_play);

        iv_download_play = (ImageView) findViewById(R.id.iv_download_play);
        iv_favo_play = (ImageView) findViewById(R.id.iv_favo_play);

        tv_totaltime_play = (TextView) findViewById(R.id.tv_totaltime_play);
        tv_underwaytime_play = (TextView) findViewById(R.id.tv_underwaytime_play);
        tv_title = (TextView) findViewById(R.id.tv_title_play);

        seekBar = (SeekBar) findViewById(R.id.sb_play);
        diskView = (DiskView) findViewById(R.id.dv_view_play);

        ib_return_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        ib_list_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        iv_favo_play.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        iv_download_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        tv_totaltime_play.setTextColor(Color.WHITE);
        tv_underwaytime_play.setTextColor(Color.WHITE);
        ib_start_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        ib_previous_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        ib_next_play.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        ib_return_play.setOnClickListener(this);
        ib_list_play.setOnClickListener(this);
        ib_next_play.setOnClickListener(this);
        ib_previous_play.setOnClickListener(this);
        ib_start_play.setOnClickListener(this);
        iv_download_play.setOnClickListener(this);
        iv_favo_play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_return_play:
                Intent intent = new Intent(PlayActivity.this, MusicListActivity.class);
                startActivity(intent);
                break;

            case R.id.ib_list_play:

                break;

            case R.id.ib_previous_play:
                previous();
            break;

            case R.id.ib_next_play:
                next();
                break;

            case R.id.ib_start_play:
            if (isPause) {
                    pause();
                } else {
                    play();
                }

            break;

            case R.id.iv_download_play:
                checkPermission();
                break;

            case R.id.iv_favo_play:
                favo();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            downloadMusic();
        }
    }
    //

    private void downloadMusic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setIcon(R.drawable.favo);
        builder.setTitle("系统提示");
        builder.setMessage("确定要下载当前歌曲吗?");
        builder.setNegativeButton("再想想", null);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String path = IURL.ROOT + music.getMusicpath();
                String name = path.substring(path.lastIndexOf("/") + 1);
                DownloadManager.downloadFile(PlayActivity.this, path, name);
            }
        });
        builder.create().show();
    }

    private void favo() {
    }

    //播放下一首歌曲
    private void next() {
        if (currentPosition > 0) {
            currentPosition++;
        } else {
            currentPosition=list.size() + 1;
        }
        play();
    }

    //播放上一首歌曲
    private void previous() {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition=list.size() - 1;
        }
        play();
    }

    //停止音乐
    private void pause() {
        isPause = false;
        diskView.stopRotate();
        music = list.get(currentPosition);
        ib_start_play.setImageResource(R.drawable.play);
        String musicPath = IURL.ROOT + music.getMusicpath();
        Intent intent = new Intent(IURL.PAUSEMUSIC_ACTION);
        intent.putExtra("musicPath", musicPath);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        play();
        registerMyReceive();
    }

    //播放音乐
    private void play() {
        isPause = true;
        diskView.startRotate();
        ib_start_play.setImageResource(R.drawable.pause);
        music = list.get(currentPosition);

        tv_title.setText(music.getName());
        tv_totaltime_play.setText(music.getDurationtime());

        String imageUrl = IURL.ROOT + music.getAlbumpic();
        MyImageLocaler.setImageBitmap(this,diskView.getCircleImageView(),imageUrl);

        String musicPath = IURL.ROOT + music.getMusicpath();
        Intent intent = new Intent(IURL.PLAYMUSIC_ACTION);
        intent.putExtra("musicPath", musicPath);
        sendBroadcast(intent);

    }

    private void registerMyReceive() {
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IURL.UPSEEKBAR_ACTION);
        filter.addAction(IURL.PLAYNEXT_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (IURL.UPSEEKBAR_ACTION.equals(action)) {
                int current = intent.getIntExtra("current", 0);
                int duration = intent.getIntExtra("duration", 0);
                int progress = current * 100 / duration;
                seekBar.setProgress(progress);
                tv_underwaytime_play.setText((new SimpleDateFormat("mm:ss").format(current)));
                tv_totaltime_play.setText((new SimpleDateFormat("mm:ss").format(duration)));
            } else if (IURL.PLAYNEXT_ACTION.equals(action)){
                next();
            }
        }
    }

    private class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekprogress = progress;
            String duration = music.getDurationtime();
            try {
                Date durationTime = new SimpleDateFormat("mm:ss").parse(duration);
                long currentTime = durationTime.getTime() * progress / 100;
                tv_underwaytime_play.setText(new SimpleDateFormat("mm:ss").format(currentTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intent intent = new Intent(IURL.UPPLAYER_ACTION);
            intent.putExtra("seekprogress", seekprogress);
            sendBroadcast(intent);
        }
    }
}


package com.example.musicmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.musicmanager.adapter.LoadMusicAdapter;
import com.example.musicmanager.constant.IURL;
import com.example.musicmanager.entity.Music;
import com.example.musicmanager.manager.HttpMusicManager;
import com.example.musicmanager.service.MyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class MusicListActivity extends AppCompatActivity implements HttpMusicManager.LoadMusicListener {
    private List<Music> list = null;
    private LoadMusicAdapter adapter;
    private ListView listView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        HttpMusicManager.asnycLoadMusic(this);
        initUI();


    }



    private void initUI() {
        listView = (ListView) findViewById(R.id.lv_musiclist);
        adapter = new LoadMusicAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MusicListActivity.this, PlayActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("list", (ArrayList<Music>)list);
                startActivity(intent);


            }
        });
    }

    @Override
    public void onLoadMusicEnd(List<Music> musics) {
        list = musics;
        adapter.addMusic(list);
        Log.d("LAG:list", list.toString());
        Intent intent = new Intent(this, MyService.class);
        startService(intent);


        Intent intent1 = new Intent();
        intent1.setAction(IURL.STARTWIDGET_ACTION);
        intent1.putExtra("list", (ArrayList)list);
        sendBroadcast(intent1);
    }
}

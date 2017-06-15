package com.example.day14;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private List<Song> mSongs=new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化下拉刷新
        setSwipeRefreshLayout();
        //初始化RecyclerView
        setRecyclerView();
        //运行时候权限检测根据检测检结果访问媒体音乐信息
        checkAccessSongsfPermission();
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutId);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark
                ,android.R.color.holo_green_light,
                android.R.color.holo_blue_dark);
    }

    /**初始化recyclerView*/
    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleSongAdapter adapter=new SimpleSongAdapter(mSongs);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        //...执行刷新动作
        loadMediaSongs();
    }
    /**权限的检测*/
    private void checkAccessSongsfPermission(){
        //1.运行时权限检测(android6.0)
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
              //申请访问权限
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},
                    200);

        }else {
            //2.从媒体库加载音乐信息
            loadMediaSongs();
        }
    }

    /**处理运行时权限申请的响应结果*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==200){
            if (grantResults.length > 0
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                //权限被允许
                loadMediaSongs();
            }
        }
    }
    /**加载媒体信息*/
    private void loadMediaSongs(){
         new SimpleSongAsyncLoader(getApplicationContext(),
                 swipeRefreshLayout).execute();

    }
}

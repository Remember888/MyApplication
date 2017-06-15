package com.example.provideraudio;

import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class Song {
    private String name;
    private String singer;
    private String data;

    public Song(String name, String singer, String data) {
        this.name = name;
        this.data = data;
        this.singer = singer;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }
}

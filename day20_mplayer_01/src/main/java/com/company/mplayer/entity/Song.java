package com.company.mplayer.entity;

/**
 * Created by pjy on 2017/5/4.
 * 借助此对象封装从媒体库获取的音乐信息
 */

public class Song {

    /**歌曲id*/
    private long id;
    /**歌曲名称*/
    private String title;
    /**歌手名字*/
    private String artist;
    /**歌曲路径*/
    private String data;
    //....
    public Song(long id,String title,String artist,String data){
        this.id=id;
        this.title=title;
        this.artist=artist;
        this.data=data;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getData() {
        return data;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

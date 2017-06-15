package com.example.musicmanager.constant;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public interface IURL {
    String ROOT="http://176.121.33.54:9080/MusicServer/";
    String MUSIC_UR=ROOT+"loadMusics.jsp";
    String PLAYMUSIC_ACTION = "com.example.musicmanager.player.PLAY";
    String PAUSEMUSIC_ACTION = "com.example.musicmanager.player.PAUSE";
    String UPSEEKBAR_ACTION = "com.example.musicmanager.player.UPSEEK";
    String PLAYNEXT_ACTION = "com.example.musicmanager.player.NEXT";
    String UPPLAYER_ACTION = "com.example.musicmanager.player.UPPLAYER";
    String STARTWIDGET_ACTION = "com.example.musicmanager.player.STARTWIDGET_ACTION";
    String UPPAUSEWIDGET_ACTION="com.example.musicmanager.player.HIDEPLAY";
    String UPPLAYWIDGET_ACTION="com.example.musicmanager.player.HIDEPAUSE";
    String PLAYNEXTWIDGET_ACTION="com.example.musicmanager.WIDGETNEXT";
}

package com.example.musicmanager.manager;

import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.musicmanager.MusicListActivity;
import com.example.musicmanager.constant.IURL;
import com.example.musicmanager.entity.Music;
import com.example.musicmanager.util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class HttpMusicManager {

    public static List<Music> loadMusicHttpGET(){
        List<Music> musics = new ArrayList<>();
        try {
            URL url = new URL(IURL.MUSIC_UR);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String jsonStr = StreamUtil.createStr(inputStream);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0;i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id");
                        String album = object.getString("album");
                        String albumpic = object.getString("albumpic");
                        String author = object.getString("author");
                        String composer = object.getString("composer");
                        String downcount = object.getString("downcount");
                        String durationtime = object.getString("durationtime");
                        String favcount = object.getString("favcount");
                        String musicpath = object.getString("musicpath");
                        String name = object.getString("name");
                        String singer = object.getString("singer");

                        Music music = new Music(id, album, albumpic, author, composer, downcount,
                                durationtime, favcount, musicpath, name, singer);
                        musics.add(music);
                }
                }else{
                    String msg = jsonObject.getString("msg");
                    Log.d("LAG:msg", "" + msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musics;
    }

    public static LoadMusicListener listener;
    public static void asnycLoadMusic(MusicListActivity loadMusicListener) {
        if (listener == null) {
            listener = loadMusicListener;
        }
        MyAsyncLoadMusicTask task = new MyAsyncLoadMusicTask();
        task.execute();
    }
    public static class MyAsyncLoadMusicTask extends AsyncTask<Void,Void,List<Music> >{


        @Override
        protected List<Music> doInBackground(Void... params) {
            List<Music> loadMusic = loadMusicHttpGET();
            return loadMusic;
        }
        @Override
        protected void onPostExecute(List<Music> musics) {
            listener.onLoadMusicEnd(musics);
        }

    }
    public interface LoadMusicListener{
        public void onLoadMusicEnd(List<Music> musics);
    }
}

package com.example.provideraudio;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class MyAscnTask extends AsyncTask<Void,Void,List<Song>>{
    private WeakReference<View> weakReference;
    private Context mcontext;

    public MyAscnTask(View view, Context context){
        mcontext= context;
        weakReference = new WeakReference<View>(view);
    }
    @Override
    protected List<Song> doInBackground(Void... params) {
        View view = weakReference.get();
        if (view == null) return null;
        Cursor cursor = mcontext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.IS_MUSIC + "= ?",
                new String[]{"1"}, null);

        List<Song> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new Song(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));
        }
        cursor.close();

        return list;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) weakReference.get();
        if (swipeRefreshLayout != null) {
            RecyclerView recyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.recyclerId);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            songs.addAll(songs);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

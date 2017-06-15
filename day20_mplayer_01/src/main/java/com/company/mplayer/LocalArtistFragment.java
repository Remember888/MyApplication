package com.company.mplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mplayer.entity.Artist;

import java.util.ArrayList;
import java.util.List;


public class LocalArtistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {



    private List<Artist> mArtists=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_local_artist, container, false);

        //1.初始化RecyclerView
        //2.初始化SwipeRefreshLayout
        //3.初始化CursorLoader加载数据


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(2,null,this);
    }

    /**
     * select artist,count(*) as ct
     * from audio
     * where artist is not null
     * group by artist
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i("TAG","LocalArtistFragment.onCreateLoader");
        if(id==2){
            return  new CursorLoader(getActivity(),
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.ARTIST,
                            "count(*) as ct" },
                    MediaStore.Audio.Media.ARTIST +" is not null ) GROUP By ( "+MediaStore.Audio.Media.ARTIST,null,null);

        }//不做具体要求
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
         Log.i("TAG","onLoadFinished.cursor");
         if(cursor==null)return;
         while(cursor.moveToNext()){
            String artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
             int count=cursor.getInt(cursor.getColumnIndex("ct"));
             mArtists.add(new Artist(artist,count));
         }
        Log.i("TAG","mArtists="+mArtists);//将此数据显示在页面上
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.example.musicmanager.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicmanager.R;
import com.example.musicmanager.constant.IURL;
import com.example.musicmanager.entity.Music;
import com.example.musicmanager.manager.MyImageLocaler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

public class LoadMusicAdapter extends BaseAdapter {
    private List<Music> list = new ArrayList<>();
    private Context context;


    public LoadMusicAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Music getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = null;
        if (convertView == null) {
            view =  View.inflate(context, R.layout.activity_musiclist_parent, null);
            holder = new ViewHolder();
            holder.image_picture = (ImageView) view.findViewById(R.id.iv_picture_list);
            holder.text_author = (TextView) view.findViewById(R.id.tv_author_list);
            holder.text_composer = (TextView) view.findViewById(R.id.tv_composer_list);
            holder.text_durationtime = (TextView) view.findViewById(R.id.tv_durationtime_list);
            holder.text_singer = (TextView) view.findViewById(R.id.tv_singer_list);
            holder.text_name = (TextView) view.findViewById(R.id.tv_name_list);
            view.setTag(holder);

        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        Music music = getItem(position);
        holder.text_name.setText(music.getName());
        holder.text_durationtime.setText(music.getDurationtime());
        holder.text_composer.setText(music.getComposer());
        holder.text_author.setText(music.getAuthor());
        holder.text_singer.setText(music.getSinger());
        String arr = IURL.ROOT + music.getAlbumpic();
        MyImageLocaler.setImageBitmap(context,holder.image_picture,arr);
        return view;
    }

    private class ViewHolder{
        private ImageView image_picture;
        private TextView text_name;
        private TextView text_singer;
        private TextView text_author;
        private TextView text_composer;
        private TextView text_durationtime;
    }
    public void addMusic(List<Music> musics) {
        if (list != null) {
            list.addAll(musics);
            notifyDataSetChanged();
        }

    }
}

package com.example.provideraudio;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.Holder> {
    private List<Song> mlist;

    public MyAdapter(List<Song> list) {
        mlist = list;
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;

        public Holder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(android.R.id.text1);
            textView2 = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Song song = mlist.get(position);
        holder.textView1.setText(song.getName());
        holder.textView2.setText(song.getSinger());
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }

    public void addAll(List<Song> song) {
        mlist.clear();
        mlist.addAll(song);
    }
}

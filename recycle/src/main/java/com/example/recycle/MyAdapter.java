package com.example.recycle;

import android.animation.LayoutTransition;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/4/22 0022.
 */

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.Holder>{
    private String[] text;
    private Integer[] image;
    public static class Holder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewId);
            imageView = (ImageView) itemView.findViewById(R.id.imageId);
        }
    }
    public MyAdapter(String[] aee, Integer[] arr){
        text = aee;
        image = arr;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(text[position]);
        holder.imageView.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return text.length;
    }
}

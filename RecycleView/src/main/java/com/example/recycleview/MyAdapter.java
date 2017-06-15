package com.example.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDatecast;
    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mtext;
        public ViewHolder(View itemView) {
            super(itemView);
            mtext = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_layout,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    public MyAdapter(String[] myDatacast ){
        this.mDatecast = myDatacast;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mtext.setText(mDatecast[position]);
    }

    @Override
    public int getItemCount() {
        return mDatecast.length;
    }
}

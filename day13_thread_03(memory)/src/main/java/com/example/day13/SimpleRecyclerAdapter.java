package com.example.day13;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pjy on 2017/5/3.
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> mObjects;

    public SimpleRecyclerAdapter(Context context,List<String> objects){
         layoutInflater=LayoutInflater.from(context);
         mObjects=objects;

    }


    static class SimpleViewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
    }
    //getView(1.创建itemview,2,创建holder并与itemview绑定，3.bind data)
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1.create item view
        View view=layoutInflater.inflate(android.R.layout.simple_list_item_1,parent, false);
        //2.create view holder and bind view
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        //3.bind data
        if(holder.itemView!=null&&holder.itemView instanceof TextView){
             ((TextView)holder.itemView)
                     .setText(mObjects.get(position));
         }
    }

    @Override
    public int getItemCount() {
        return mObjects!=null?mObjects.size():0;
    }

    public void addAll(List<String> objects){
        mObjects.addAll(objects);
    };
}

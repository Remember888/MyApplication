package com.example.recycleviewrefresh;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>{
    private List<String> mlist;
    private LayoutInflater layoutInflater;
    private View v;
    public MyAdapter(Context context, List<String> list) {
        layoutInflater = LayoutInflater.from(context);
        mlist = list;
    }

    public void addAll(List<String> strings) {
        mlist.addAll(strings);
    }

    public void addAll(int order, List<String> s) {
        mlist.addAll(order, s);
    }

    class Holder extends RecyclerView.ViewHolder {
        private View textView;
        public Holder(View itemView) {
            super(itemView);
            textView = itemView;
        }
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (holder.textView != null && holder.textView instanceof TextView) {
            ((TextView)(holder.textView)).setText(mlist.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mlist!=null? mlist.size():0;
    }
}

package com.example.newsfragmenttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/4/16 0016.
 */

public class NewsAdapter extends ArrayAdapter {
    private int res;
    public NewsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        res = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = (News) getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(res, null);
        }else{
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.tv_list);
        text.setText(news.getTitle());

        return view;
    }
}

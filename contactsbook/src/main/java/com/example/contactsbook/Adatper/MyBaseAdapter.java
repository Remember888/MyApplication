package com.example.contactsbook.Adatper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.contactsbook.entry.Contact;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/17 0017.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    List<T> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public MyBaseAdapter(Context context) {
        this.context = context;
    }
    //数据增加
    public void addDatas(List<T> data,boolean isClean){
        if (isClean) {
            list.clear();
        }
        if (data != null) {
            list.addAll(data);
            notifyDataSetChanged();
        }
    }
    //删除全部数据
    public void removeAllDatas(){
        if (list != null) {
            list.clear();
        }
    }



    //删除数据
    public void removeDatas(List<T> data) {
        list.remove(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        Log.d("ld", list.size()+"");
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent) ;
}

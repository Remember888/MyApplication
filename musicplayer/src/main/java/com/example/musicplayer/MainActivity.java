package com.example.musicplayer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 *
 * Created by Administrator on 2017/4/17 0017.
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private String[] name  = { "本地播放","最近播放","我的收藏"};
    private int[] age = {R.mipmap.a4w,R.mipmap.a4z,R.mipmap.aig};
    private ListView list;
    private ArrayAdapter<String>  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        list = (ListView) findViewById(R.id.lv_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView text = (TextView) super.getView(position, convertView, parent);
                Drawable left = getResources().getDrawable(age[position]);
                left.setBounds(0, 0, 50, 50);
                text.setCompoundDrawables(left,null,null,null);
                return text;
            }
        };
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    /**
     * 监视列表
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (name[position]) {
            case "本地播放":
                break;
            case "最近播放":
                break;
            case "我的收藏":
                break;
            default:
                break;
        }
    }
}

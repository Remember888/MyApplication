package com.example.simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SimpleAdapter simpleAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_list);

        simpleAdapter = new SimpleAdapter(this, getChildContent(), R.layout.secondlayout, new String[]{"image", "title", "content"}, new int[]{R.id.iv_view,R.id.tv_view1, R.id.tv_view2});
        listView.setAdapter(simpleAdapter);
    }

    public List<? extends Map<String,?>> getChildContent() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("image", R.drawable.emo_im_angel);
        map.put("title", "lala");
        map.put("content", "很高兴");
        list.add(map);
        map = new HashMap<>();
        map.put("image", R.drawable.emo_im_cool);
        map.put("title", "haha");
        map.put("content", "得意");
        list.add(map);
        map = new HashMap<>();
        map.put("image", R.drawable.emo_im_crying);
        map.put("title", "aaa");
        map.put("content", "沮丧");
        list.add(map);
        map = new HashMap<>();
        map.put("image", R.drawable.emo_im_happy);
        map.put("title", "lala");
        map.put("content", "很高兴");
        list.add(map);
        map = new HashMap<>();
        map.put("image", R.drawable.emo_im_yelling);
        map.put("title", "lala");
        map.put("content", "很高兴");
        list.add(map);


        return list;
    }
}

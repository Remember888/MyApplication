package com.example.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.recycleview.R.id.RV_view;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView iv_convation;
    private TextView text_title;
    private TextView text_content;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(RV_view);
        text_title = (TextView) findViewById(R.id.tv_title);
        text_content = (TextView) findViewById(R.id.tv_content);
        manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        String[] s = {"zhang","xiao","ming","shi",};
        adapter = new MyAdapter(s);
        recyclerView.setAdapter(adapter);
    }
}

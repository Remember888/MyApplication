package com.example.recycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    private String[] arr = {"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
    private Integer[] image = {R.mipmap.png_13,R.mipmap.png_14,R.mipmap.png_15,R.mipmap.png_16,
            R.mipmap.png_17,R.mipmap.png_18,R.mipmap.png_19,R.mipmap.png_20,R.mipmap.png_21,
            R.mipmap.png_22,R.mipmap.png_23,R.mipmap.png_24,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(arr, image);
        recyclerView.setAdapter(myAdapter);
    }
}

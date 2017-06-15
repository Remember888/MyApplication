package com.example.day04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBarId);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Tile-A");
        //让toolbar替换了actionbar
        setSupportActionBar(toolbar);

        LinearLayout layout= (LinearLayout)
                findViewById(R.id.linearLayoutId);

        for(int i=0;i<100;i++){
            TextView textView=new TextView(this);
            textView.setTextSize(30);
            textView.setText("Text-"+i);
            layout.addView(textView);
        }
    }
}

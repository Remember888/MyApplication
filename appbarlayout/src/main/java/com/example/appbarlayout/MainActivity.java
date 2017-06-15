package com.example.appbarlayout;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TabLayout tab;
    private Toolbar toob;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toob = (Toolbar) findViewById(R.id.tool);
        toob.setTitle("ADC");
        toob.setLogo(R.mipmap.ic_launcher);
        linearLayout = (LinearLayout) findViewById(R.id.ll_layout);
        for (int i = 0; i < 100; i++) {
            TextView textView = new TextView(this);
            textView.setText("text" + i);
            textView.setTextSize(20);
            linearLayout.addView(textView);
        }
    }
}

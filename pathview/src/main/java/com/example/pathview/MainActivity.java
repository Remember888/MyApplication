package com.example.pathview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PathView group = (PathView) findViewById(R.id.RG_group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                PathView pathView = new PathView(MainActivity.this);
                int position = group.indexOfChild(group.findViewById(checkedId));
                pathView.upstart(position);
            }
        });
    }
}

package com.example.musicplays;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
    }

    public void onClick01(View v) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action", MyService.PLAY);
        startService(intent);
    }
    public void onClick02(View v) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action", MyService.PAUST);
        startService(intent);
    }
    public void onClick03(View v) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action", MyService.STOP);
        startService(intent);
    }
}

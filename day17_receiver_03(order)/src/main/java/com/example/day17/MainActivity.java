package com.example.day17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick01(View v){
        String action="action.ABC";
        Intent target=new Intent(action);
        String receiverPermission="permission.ABC";
        //发送有序广播(多可以接收者要顺序接收广播)
        sendOrderedBroadcast(target,receiverPermission);
    }
}

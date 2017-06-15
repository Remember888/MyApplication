package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AtyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new MainActivity.AtyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("action.DOWN01");
        //registerReceiver(receiver,filter); //允许跨进程接收
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver,filter);//只接收进程内部的广播
    }
    public void onClick01(View v){
        startService(new Intent(this,DownService.class));
    }
    class AtyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Button btn= (Button) findViewById(R.id.btnId);
            String text=intent.getStringExtra("resultKey");
            btn.setText(text);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // unregisterReceiver(receiver);

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(receiver);
    }
}

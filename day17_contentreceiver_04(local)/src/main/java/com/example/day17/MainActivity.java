package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AtyReceiver receiver;
    private MyButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (MyButton) findViewById(R.id.btnId);
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
            String text=intent.getStringExtra("resultKey");
            if(TextUtils.isEmpty(text)){
                float progress=intent.getFloatExtra("progressKey",0);
                btn.setProgress(progress*1.0f/100);
                btn.setText(progress+"%");
                btn.invalidate();
            }else{
                btn.setText(text);
            }
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

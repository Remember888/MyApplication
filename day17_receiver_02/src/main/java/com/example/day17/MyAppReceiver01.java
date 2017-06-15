package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAppReceiver01 extends BroadcastReceiver {
    private int count;
    public MyAppReceiver01() {
        Log.i("TAG","MyAppReceiver01()");
    }

    /**此方法运行在主线程，用于处理接收到广播以后的业务*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","MyAppReceiver01.onReceive.count="+count++);
    }
}

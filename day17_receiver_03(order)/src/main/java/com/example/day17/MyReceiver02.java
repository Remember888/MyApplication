package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver02 extends BroadcastReceiver {
    public MyReceiver02() {
        Log.i("TAG","MyReceiver02()");
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("TAG","MyReceiver02.onReceive");
        //向下一个广播接收者传递数据
        setResultData("....");
        //终止广播继续传递
        abortBroadcast();
    }
}

package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver01 extends BroadcastReceiver {
    public MyReceiver01() {
        Log.i("TAG","MyReceiver01()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("TAG","MyReceiver01.onReceive");
    }
}

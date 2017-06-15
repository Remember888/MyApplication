package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver03 extends BroadcastReceiver {
    public MyReceiver03() {
        Log.i("TAG","MyReceiver03()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("TAG","MyReceiver03.onReceive");
    }
}

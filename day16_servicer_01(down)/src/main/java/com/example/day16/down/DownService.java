package com.example.day16.down;

import android.content.Intent;
import android.util.Log;

/**
 * Created by pjy on 2017/5/8.
 */

public class DownService extends AbsService {
    @Override
    public void onHandleIntent(Intent intent) {
        String tname=Thread.currentThread().getName();
        Log.i("TAG","thread.name="+tname);
        Log.i("TAG","down start");
        try{Thread.sleep(5000);}catch (Exception e){}
        Log.i("TAG","down start end");
    }
}

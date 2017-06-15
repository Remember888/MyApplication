package com.example.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class MySeriveIntent extends AbsService {

    @Override
    public void onHandleIntent(Intent intent) {
        String tname = Thread.currentThread().getName();
        Log.i("TAG","thread.name="+tname);
        Log.i("TAG","down start");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("main", "AGD");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


}

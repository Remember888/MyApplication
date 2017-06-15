package com.example.day17;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by pjy on 2017/5/9.
 */

public class DownService extends IntentService {

    /**自己写的service 中要有一个无参的构造函数*/
    public DownService() {
        super("DownService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("TAG","down-start");
        try{Thread.sleep(5000);}catch(Exception e){}
        Log.i("TAG","down-end");
        //发送广播，然后更新UI
        Intent target=new Intent("action.DOWN01");
        target.putExtra("resultKey","down-ok");
        //sendBroadcast(target);//此形式的发送可以跨进程接收

        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(target);//发送广播，进程内部传递

    }
}

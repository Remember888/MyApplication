package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public class AbsService extends Service {
    private MyHandler handler;
    private HandlerThread thread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        thread = new HandlerThread("worder");
        thread.start();
        handler = new MyHandler(thread.getLooper());
    }
    class MyHandler extends Handler{
        public MyHandler(Looper looper) {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onHandleIntent((Intent) msg.obj);
            stopSelf(msg.arg1);
        }
    }

    public void onHandleIntent(Intent intent) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = Message.obtain();
        message.obj = intent;
        message.arg1 = startId;
        handler.sendMessage(message);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.quit();
    }
}

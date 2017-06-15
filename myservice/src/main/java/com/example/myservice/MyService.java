package com.example.myservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class MyService extends Service {
    @Nullable
    private  DownloadBinder binder = new DownloadBinder();
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Notification comes")
                .setContentText("This is content")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.layout.activity_main))
                .build();

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startForeground(1,notification);        Log.d("MyService", "onCreate executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "Start Service");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d("MyService","MyService start");
        return super.startService(service);
    }


    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("Mysevice","startDownload execued");
        }
        public void stopDownload(){
            Log.d("Mysevice", "stopDownload execued");
        }

    }

}

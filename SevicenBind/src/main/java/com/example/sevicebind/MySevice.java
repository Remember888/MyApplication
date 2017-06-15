package com.example.sevicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public class MySevice extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        Log.i("MyService", "onDestroy");
        super.onDestroy();
    }

    public void play() {
        Log.i("MyService", "play");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyService", "onUnbind");
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder{
        public MySevice getService(){
            return  MySevice.this;
        }
    }
}

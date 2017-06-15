package com.example.day16.bind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService01 extends Service {
    private static final String TAG=MyService01.class.getSimpleName();
    public MyService01() {
        Log.i("TAG","MyService01()");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }
    /**此方法应用于service绑定模式，用于返回一个binder对象*/
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        //return new Binder();//实现了IBinder接口
        return new LocalBinder();//绑定成功以后在activity可以获得此对象
    }
    class LocalBinder extends  Binder{
        public MyService01 getService(){
            return MyService01.this;
        }
    }

    /**需求：在activity中调用service对象play方法*/
    public void play(){
        Log.i("TAG","service.play()");
    }

    /**Service对象解绑时会执行此方法*/
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);

    }



    /**Service对象销毁时自动执行此方法*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}

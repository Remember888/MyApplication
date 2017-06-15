package com.example.day16.bind;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onBindClick(View v){

        Intent target=new Intent(this,MyService01.class);
        int flags= Service.BIND_AUTO_CREATE;
        ServiceConnection connection=this;
        //绑定service
        bindService(target,connection,flags);


    }
    public void onUnbindClick(View v){

        unbindService(this);//this 指向serviceconnection对象
    }

    /**Service绑定成功以后自动执行*/
    @Override
    public void onServiceConnected(ComponentName name,
                                   IBinder binder) {
        MyService01 service=
        ((MyService01.LocalBinder)binder).getService();
        Log.i("TAG","onServiceConnected");
        service.play();
    }

    /**service非正常解绑以后会执行*/
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("TAG","onServiceDisconnected");
    }
}

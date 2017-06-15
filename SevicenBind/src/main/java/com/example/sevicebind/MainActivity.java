package com.example.sevicebind;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
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

    public void onBind(View v) {
        Intent intent = new Intent(this,MySevice.class);

        int flags = Service.BIND_AUTO_CREATE;
        ServiceConnection conn = this;
        bindService(intent, conn, flags);
    }

    public void onUnBind(View v) {
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        MySevice sevice = ((MySevice.MyBinder)binder).getService();
        Log.i("TAG", "onService");
        sevice.play();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("TAG", "disconnected");
    }
}

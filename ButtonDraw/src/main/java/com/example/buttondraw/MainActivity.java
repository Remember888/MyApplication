package com.example.buttondraw;

import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WorkHandler workHandler;
    private MainHandler mainHandler;
    private HandlerThread handlerThread;
    private MyButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (MyButton) findViewById(R.id.btn_draw);
        button.setOnClickListener(this);
        handlerThread = new HandlerThread("woker");
        handlerThread.start();
        workHandler = new WorkHandler(handlerThread.getLooper());
        mainHandler = new MainHandler(Looper.getMainLooper());
    }

    @Override
    public void onClick(View v) {
        Message message = Message.obtain();
        message.arg1 = 100;
        workHandler.sendMessage(message);
    }

    class WorkHandler extends Handler{
        public WorkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            for (int i = 0; i <= msg.arg1; i++) {
                try{Thread.sleep(2000);}catch (Exception e){e.printStackTrace();}

                Message message = Message.obtain();
                message.arg1 = i;
                mainHandler.sendMessage(message);
            }
        }
    }
    class MainHandler extends Handler{
        public MainHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            button.setText(msg.arg1 + "%");
            button.setTextColor(Color.BLUE);
            button.setPercent(msg.arg1 * 1.0f / 100);
            button.invalidate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}

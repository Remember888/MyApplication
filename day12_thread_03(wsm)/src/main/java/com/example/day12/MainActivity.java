package com.example.day12;

import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private HandlerThread handlerThread;
    private WorkerHandler workerHandler;
    private MyButton downBtn;
    private MainHandler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downBtn = (MyButton) findViewById(R.id.btnId);
        //启动工作线程(底层启动以后会自动创建looper)
        handlerThread = new HandlerThread("woker");
        handlerThread.start();
        //构建handler关联工作线程的looper
        workerHandler = new WorkerHandler(
                handlerThread.getLooper());
        //构建handler关联当前线程looper(主线程)
        //mainHandler = new MainHandler();
        mainHandler = new MainHandler(Looper.getMainLooper());
    }
    class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
             //通过此循环模拟下载过程
             for(int i=1;i<=msg.arg1;i++){
                try{Thread.sleep(200);}catch (Exception e){}

                 //runOnUiThread
                 //给主线程发消息
                 Message newMsg=Message.obtain();
                 newMsg.arg1=i;
                 mainHandler.sendMessage(newMsg);
             }
        }
    }
    class MainHandler extends  Handler{
        public MainHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
             downBtn.setProgress(msg.arg1*1.0f/100);
             downBtn.setTextColor(Color.RED);
             downBtn.setText(msg.arg1+"%");
             downBtn.invalidate();//重新绘制
        }
    }
    public void onClick(View v){
        //Message msg=new Message();//不推荐
        Message msg=Message.obtain();//推荐(首先从消息池)
        msg.arg1=100;
        workerHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}

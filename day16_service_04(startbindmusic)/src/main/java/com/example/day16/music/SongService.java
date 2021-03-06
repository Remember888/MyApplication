package com.example.day16.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SongService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{
    public SongService() {}
    public static final String STOP="action.STOP";
    /**借助此属性记录当前播放位置*/
    private int currentPosition;
    private MediaPlayer mediaPlayer;
    private NotificationManager nManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG","onCreate");
        //创建NotificationManager
        nManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //初始化mediaPlayer
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG","onStartCommand");
        String action=null;
        if(intent!=null){
            action=intent.getAction();
        }
        Log.i("TAG","action="+action);
        if(STOP.equals(action)){
            stopSelf();//在service内部停止 service
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TAG","onBind");
        return new InnerBinder();
    }
    public class InnerBinder extends Binder implements  IPlayer{
        public void playMusic(){
            play();
        }
        public void pauseMusic(){
            pasue();
        }
    }
    private void play(){
        //重置媒体播放器
        mediaPlayer.reset();
        try {
            //设置要播放的音乐的路径
            mediaPlayer.setDataSource("/storage/emulated/0/Music/AA.mp3");
            //异步加载音乐
            mediaPlayer.prepareAsync();
        }catch(Exception e){e.printStackTrace();}
        //创建并发送一个Notification
        createOrUpdateNotification();
    }


    private void pasue(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            currentPosition=mediaPlayer.getCurrentPosition();
        }
    }
    private void createOrUpdateNotification(){
        //1.create Notification
        Notification ntf=
        new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("artist")
                //设置延迟意图(希望点击通知时停止音乐播放)
                .setContentIntent(PendingIntent.getService(
                        this,100,new Intent(STOP),//隐式意图(点击通知停止service)
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
        //2.notify notification
        nManager.notify(1,ntf);
        //3.将service与notification对象绑定在一起
        startForeground(1,ntf);//此时service所在进程为前台进程

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("TAG","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG","onDestroy");
        //结束service与notification的绑定
        stopForeground(true);
        //退出通知
        nManager.cancel(1);
        //释放资源
        if(mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完成开启循环播放模式
        play();
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        //指定到具体播放位置
        Log.i("TAG","currentPosition="+currentPosition);
        mp.seekTo(currentPosition);
        mp.start();
    }
}

package com.example.threadlist;

import android.os.Looper;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class WordThread extends Thread {
    private Looper looper;
    @Override
    public void run() {
        synchronized (this) {
            Looper.prepare();
            looper = Looper.myLooper();
            this.notifyAll();
        }
        Looper.loop();
    }
    public Looper getLooper(){
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && looper == null) {
                try{this.wait();} catch(Exception e){e.printStackTrace();};
            }
        }
        return looper;
    }
    public void quit(){
        looper = getLooper();
        if (looper != null) {
            looper.quit();
        }
    }
}

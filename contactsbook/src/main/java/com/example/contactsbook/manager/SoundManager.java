package com.example.contactsbook.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class SoundManager {

    private static SoundPool soundPool = null;

    public static void setSoundPool(Context context,int resId) {
        if (soundPool == null) {
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
        soundPool.load(context, resId, 1);
    }
    public static void release(){
        if (soundPool != null) {
            soundPool.release();
        }
    }
}

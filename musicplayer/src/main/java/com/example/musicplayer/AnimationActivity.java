package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.gesture.GestureUtils;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class AnimationActivity extends Activity {
    private ImageView ivhome;
    private TextView tvhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        setAnimationVistion();
        setAnimationVersion();
    }

    /**
     * 获得版本信息
     */
    private void setAnimationVersion() {
        tvhome = (TextView) findViewById(R.id.tv_home);
        PackageManager manager = getPackageManager();

        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            tvhome.setText("版本号："+info.versionName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置动画效果
     */
    private void setAnimationVistion() {
        ivhome = (ImageView) findViewById(R.id.iv_home);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3000);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(AnimationActivity.this, GuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivhome.setAnimation(alpha);
    }
}

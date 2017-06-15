package com.example.weatherreport;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.apache.http.conn.scheme.HostNameResolver;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @ViewInject(R.id.iv_splash)
    ImageView iv_splash;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initAnimation();
        stopAnimation();
    }

    private void stopAnimation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void initAnimation() {
        iv_splash.setBackgroundResource(R.drawable.splash_anima);
        animationDrawable = (AnimationDrawable) iv_splash.getBackground();
        animationDrawable.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}

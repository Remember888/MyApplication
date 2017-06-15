package com.example.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.groupon.R;
import com.example.groupon.constant.MyConstant;
import com.example.groupon.util.SPUtil;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            private Intent intent;
            @Override
            public void run() {
                SPUtil spUtil = new SPUtil(SplashActivity.this);
                boolean first = spUtil.isFrist();
                intent = new Intent(SplashActivity.this, GuideActivity.class);
                /*if (first) {
                   spUtil.setUtil(false);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }*/
                startActivity(intent);
            }
        }, 1000);
    }
}

package com.example.mynewsclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mynewsclient.utils.PreferUtils;
import com.example.mynewsclient.utils.PreferUtils;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    private RelativeLayout layout;
    private Timer timer;
    private TextView myView;
    //	private MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        layout = (RelativeLayout) findViewById(R.id.rl_Id);


        //旋转
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);


        //缩放
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        //平移
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        layout.setAnimation(set);
        set.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        PreferUtils utils = new PreferUtils();
        boolean isFristopen = utils.getBoolean(this, "isFristopen", true);
        if (isFristopen){
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        PreferUtils utils = new PreferUtils(); //自己定义的包装类，位于com.example.newsclient.utils中
        utils.setBoolean(this, "isFristopen", false);
    }
}

package com.example.contactsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class GuideActivity extends Activity implements Animation.AnimationListener {

    private ImageView imageView;
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        imageView = (ImageView) findViewById(R.id.iv_guide);
        setAnimation();
    }

    private void setAnimation() {
        scaleAnimation = new ScaleAnimation(0.5f,1,0.5f,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setAnimationListener(this);
        scaleAnimation.setDuration(2000);

        alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);

        AnimationSet set = new AnimationSet(true);
        set.setDuration(3000);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);

        imageView.setAnimation(set);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(GuideActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.guide_out,R.anim.layout_in);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

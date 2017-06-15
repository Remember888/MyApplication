package com.example.musicmanager.view;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.musicmanager.R;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class DiskView extends RelativeLayout {

    private final FrameLayout fragment_disc;
    private final CircleImageView circleImageView;
    private final ImageView imagePin;

    public DiskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.include_music_layout, this);
        fragment_disc = (FrameLayout) view.findViewById(R.id.fl_disc_view);
        circleImageView = (CircleImageView) view.findViewById(R.id.ci_gravity_picture);
        imagePin = (ImageView) view.findViewById(R.id.iv_pin);
    }
    public void startRotate(){
        fragment_disc.clearAnimation();
        imagePin.clearAnimation();
        RotateAnimation rotateAnimation = new RotateAnimation(0, 25, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(2000);
        imagePin.setAnimation(rotateAnimation);

        RotateAnimation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(10000);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setFillAfter(false);
        animation.setInterpolator(new LinearInterpolator());
        fragment_disc.setAnimation(animation);
    }
    public void stopRotate(){
        fragment_disc.clearAnimation();
        imagePin.clearAnimation();
        RotateAnimation rotateAnimation = new RotateAnimation(25, 0, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(2000);
        imagePin.setAnimation(rotateAnimation);

    }

    public void setAblumpic(Bitmap bitmap) {
        circleImageView.setImageBitmap(bitmap);
    }

    public void setAblumpic(int resources) {
        circleImageView.setImageResource(resources);
    }
    public CircleImageView getCircleImageView(){
        return circleImageView;
    }
}

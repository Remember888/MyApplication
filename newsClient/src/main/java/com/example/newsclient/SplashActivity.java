package com.example.newsclient;

import java.util.Timer;
import java.util.TimerTask;

import com.example.newsclient.base.MyView;
import com.example.newsclient.utils.PreferUtils;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity implements AnimationListener {
	private RelativeLayout layout;
	private Timer timer;
	private TextView myView;
//	private MyView myView;
	public static int i = 5;
	/*private Handler handler= new Handler(){
		public void handleMessage(Message msg) {
			i -= msg.what;
			String gString = i + "";
			myView = (TextView) findViewById(R.id.tv_Id);
			myView.setText(gString);
			if (i == 0) {
				timer.cancel();
				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent);
				Log.i("MainActivity", "�ɹ���ת");
				
			
			}
		};
	};*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		layout = (RelativeLayout) findViewById(R.id.rl_Id);
		
		
		//��ת
		RotateAnimation rotate = new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);
		rotate.setFillAfter(true);
		
		
		//����
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setFillAfter(true);
		
		//ƽ��
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
	//ʵ�ֵ���ʱ
	private void startRun() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				
				message.what = 1;
				//handler.sendMessage(message);
			}
		}, 100, 1000);
		
		new Thread(){
			public void run() {
				SystemClock.sleep(2000);
				//handler.sendEmptyMessage(2);
			};
		}.start();
	}
	@Override
	public void onAnimationStart(Animation animation) {
		
	
	}
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		//startRun();
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
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		PreferUtils utils = new PreferUtils(); //�Լ�����İ�װ�࣬λ��com.example.newsclient.utils��
		utils.setBoolean(this, "isFristopen", false);
	}
}

package com.example.newsclient.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
	private String mtext;

	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyView(Context context,String text) {
		super(context);
		// TODO Auto-generated constructor stub
		mtext = text;
	}
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint  paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(420, 60, 15, paint);
		Paint paint1 = new Paint();
		paint1.setColor(Color.BLACK);
		paint1.setTextSize(12);
		canvas.drawText(mtext, 412, 48, paint1);
	}

}

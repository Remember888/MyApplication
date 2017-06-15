package com.example.buttondraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

import static android.R.attr.right;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class MyButton extends Button {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float percent;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.YELLOW);
        float left = 0;
        float top = 0;
        float botton = getBottom();
        float right = (getWidth()) * percent;
        canvas.drawRect(left, top, right, botton, paint);
    }
}

package com.example.day08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pjy on 2017/4/25.
 */

public class CircleIndicator extends View {
    public CircleIndicator(Context context) {
        super(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**空心圆x轴位置*/
    private float skrokeStartXPos;
    private float fillStartXPos;
    private float skrokeStartYPos;
    /**此方法是在页面大小发生变化时执行,例如横竖切换屏幕*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        skrokeStartXPos=fillStartXPos=w/2-3*radius;
        skrokeStartYPos=h/2;
    }

    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius=50;
    /**此方法是在view的draw方法中执行的调用,
     * 用于绘制view的内容
     * @param canvas  代表一个画板对象
     * */
    @Override
    protected void onDraw(Canvas canvas) {//CircleIndicator
        super.onDraw(canvas);
        //设置画笔样式(线条)
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔颜色
        paint.setColor(Color.BLACK);
        //设置画笔线条粗细
        paint.setStrokeWidth(10);
        //执行绘制动作(绘制三个空心圆)
        for (int i=0;i<3;i++) {
            canvas.drawCircle(skrokeStartXPos+3*radius*i, skrokeStartYPos, radius, paint);
        }
        //绘制一个实心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(fillStartXPos,skrokeStartYPos,radius-5,paint);
    }
    public void updateFillCirclePos(int position,float offset){
        fillStartXPos=skrokeStartXPos+3*radius*(position+offset);
        //重新绘制(invalidate()方法被调用后会重新执行view的onDraw方法)
        invalidate();
    }
}

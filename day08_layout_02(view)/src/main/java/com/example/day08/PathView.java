package com.example.day08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pjy on 2017/4/25.
 */

public class PathView extends View {
    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.设置画笔对象
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        //2.绘制path图形
        //2.1构建path对象
        Path path=new Path();
        //2.2设置path对象起始点
        path.moveTo(100,100);
        //2.3设置path路径的终点
        path.lineTo(100,200);
        //path.lineTo(200,200);
        //path.lineTo(260,300);
        path.lineTo(200,200);
        path.close();//将图形形成一个闭环(将起点和终点连接一下)
        canvas.drawPath(path,paint);
        //绘制一个文本
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("三角形",100,300,paint);
    }
}

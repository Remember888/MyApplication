package com.example.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.media.RatingCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class Paths extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paths(Context context) {
        super(context);
    }

    public Paths(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Paths(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Paths(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0, 100);
        path.lineTo(0, 200);
        path.lineTo(200, 200);
        canvas.drawPath(path, paint);
        Path path1 = new Path();
        path1.moveTo(0, 200);
        path1.lineTo(100, 50);
        path1.lineTo(100, 60);
        path1.lineTo(110, 70);
        path1.lineTo(120, 100);
        path1.lineTo(150, 100);
        canvas.drawPath(path1,paint);

    }
}

package com.example.clickcleiditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class ClickcleIdtor extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float skrokeStartXPos;
    private float skrokeStartYPos;
    private float fillStartXPos;
    private float redius = 20;

    public ClickcleIdtor(Context context) {
        super(context);
    }

    public ClickcleIdtor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickcleIdtor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClickcleIdtor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        skrokeStartXPos = fillStartXPos = w/ 2 - 3 * redius;
        skrokeStartYPos = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        for (int i = 0; i < 3; i++) {
            canvas.drawCircle(skrokeStartXPos+3*redius*i,skrokeStartYPos,redius,paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(fillStartXPos,skrokeStartYPos,redius,paint);
    }

    public void upDateFillPos(int possible, float s) {
        fillStartXPos = skrokeStartXPos + 3 * redius * (possible + s);
        invalidate();
    }
}

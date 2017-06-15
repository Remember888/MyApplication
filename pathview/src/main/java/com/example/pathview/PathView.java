package com.example.pathview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class PathView extends RadioGroup {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int redLineStartX = 0;
    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(3);

        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        int height = getHeight();
        int width = getWidth();
        path.moveTo(0, height);
        path.lineTo(width,height);
        canvas.drawPath(path,paint);
        paint.setColor(Color.BLUE);
        Path path1 = new Path();
        path1.moveTo(redLineStartX,height);
        path1.lineTo(redLineStartX + getWidth() / getChildCount() , height);
        canvas.drawPath(path1,paint);
        canvas.restore();
    }
    public void upstart(int position){
        redLineStartX = position * (getWidth() / getChildCount());
        invalidate();
    }

}

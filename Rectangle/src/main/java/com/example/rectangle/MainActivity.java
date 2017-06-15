package com.example.rectangle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Canvas canvas;
    private Paint paint;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_view);
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.lyf);
        int height = bitmap1.getHeight();
        int width = bitmap1.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawCircle(width / 2, height / 2, 100, paint);
        canvas.drawBitmap(bitmap1, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setStrokeWidth(5);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2,height/2,100,paint);
       /* bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, 200, 200, paint);
        paint.setColor(Color.BLUE);
        String text = "tarema";
        paint.setTextSize(20);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text,100-rect.width()/2,100+rect.height()/2,paint);*/
        imageView.setImageBitmap(bitmap);
    }
}

package com.example.groupon.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.groupon.ui.MainActivity;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class Btn_Skip_Util {
    public static void setClick(Button button, final Context context) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }
}

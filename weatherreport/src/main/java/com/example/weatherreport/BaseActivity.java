package com.example.weatherreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Field;

public abstract  class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(svedInstanceState);
        setContentView(getLayoutId());
        autoInjectAllFieldId();
    }
    public abstract int getLayoutId();

    private void autoInjectAllFieldId() {
        Class clz = this.getClass();
        Field[] field = clz.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            if (field[i].isAnnotationPresent(ViewInject.class)){
                ViewInject in = field[i].getAnnotation(ViewInject.class);
                int value = in.value();
                if (value > 0) {
                    field[i].setAccessible(true);
                    try {
                        field[i].set(this,this.findViewById(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

package com.example.groupon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.groupon.constant.MyConstant;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class SPUtil {

    private static SharedPreferences pre;
    private static SharedPreferences.Editor editor;

    public SPUtil(Context context) {
        pre = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SPUtil(Context context, String first) {
        pre = context.getSharedPreferences(first, Context.MODE_PRIVATE);
    }
    public  void setUtil(boolean flag){
        editor = pre.edit();
        editor.putBoolean(MyConstant.FIRST, flag);
        editor.commit();
    }
    public boolean isFrist(){
        return pre.getBoolean(MyConstant.FIRST, true);
    }

}

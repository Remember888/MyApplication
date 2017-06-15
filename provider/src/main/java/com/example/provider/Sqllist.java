package com.example.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class Sqllist extends SQLiteOpenHelper {
    private Context mcontext;
    public static final String CREATE_BOOK = "create table Book("+
           "id integer primary key autoincrement," +
            "auto text," +
            "price real," +
            "pages integer," +
            "name text)";
    public Sqllist(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

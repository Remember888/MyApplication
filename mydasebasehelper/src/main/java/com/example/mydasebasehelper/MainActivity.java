package com.example.mydasebasehelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyDasebaseHelp db;
    private SQLiteDatabase base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDasebaseHelp(this, "Book.db", null, 1);
        base = db.getWritableDatabase();
        base.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("", "");
            base.insert("Book", null, values);
            base.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            base.endTransaction();
        }
        ContentValues values = new ContentValues();
        values.put("", "");
        values.put("","");
        base.insert("Book", null, values);
        Cursor cursor = base.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                cursor.getString(1);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}

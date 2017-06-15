package com.example.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button create;
    private Button add;
    private Button update;
    private Button delete;
    private Button query;
    private Sqllist db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Sqllist(this, "BookStroe.db", null, 1);
        setCreateSQL();
        setAddDate();
       // setUpdate();
        //setDeleteDate();
      //  setQuery();

    }
    //创建数据库
    private void setCreateSQL() {
        create = (Button) findViewById(R.id.btn_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getWritableDatabase();
            }
        });
    }

    private void setAddDate() {
        add = (Button) findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sql = db.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("auto", "Dan Brawn");
                values.put("name","xiaozheng");
                values.put("pages", 489);
                values.put("price", 16.56);
                sql.insert("Book", null, values);
                sql.close();
                ContentValues values1 = new ContentValues();
                values.put("auto", "Tank");
                values.put("name", "xu");
                values.put("pages", 799);
                values.put("price", 20.00);
                sql.insert("Book", null, values1);
            }
        });

    }

    private void setUpdate() {
        update = (Button) findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("auto","dfsa");
                sql.update("Book",values,"name = ?",new String[]{
                    "xiaozheng"});
            }
        });
    }

    private void setDeleteDate() {
        delete = (Button) findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sql = db.getReadableDatabase();
                sql.delete("Book","name = ?",new String[]{
                        "xiaozheng"
                });
            }
        });
    }

    private void setQuery() {
        query = (Button) findViewById(R.id.btn_query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String auto = cursor.getString(cursor.getColumnIndex("auto"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("name", name);
                        Log.d("auto", auto);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}

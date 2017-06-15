package com.example.threadlist;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private WordThread wordThread;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_list);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        wordThread = new WordThread();
        wordThread.start();
        handler = new Handler(wordThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    loadData();
                }
            }
        };
        handler.sendEmptyMessage(1);
    }

    private void loadData() {
        try{Thread.sleep(3000);}catch(Exception e){e.printStackTrace();}
        String s = "A/B/C/D/E/F/G";
        String[] arr = s.split("/");
        list.addAll(Arrays.asList(arr));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, 101, 201, " 刷新");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 101) {
            handler.sendEmptyMessage(1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wordThread.quit();
    }
}

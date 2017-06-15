package com.example.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private EditText edit_nickName;
    private EditText edit_content;
    private LinearLayout ll_nickName;
    private LinearLayout ll_content;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        edit_nickName = (EditText) findViewById(R.id.et_main_nickname);
        edit_content = (EditText) findViewById(R.id.et_main_content);
        TextView textView = (TextView) findViewById(R.id.tv_view);
        listView.setEmptyView(textView);
        ll_nickName = (LinearLayout) findViewById(R.id.ll_nickname);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
    }

    public void connect(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("176.121.33.48",5000);
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (flag) {
                                    final String readline = reader.readLine();
                                    if (readline == null) {
                                        throw new RuntimeException("服务端已关闭");
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            list.add(readline);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_content.setVisibility(View.GONE);
                            ll_nickName.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

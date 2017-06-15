package com.tedu.employeemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tedu.employeemanager.Manager.HttpManager;
import com.tedu.employeemanager.adapter.EmployeeAdapter;
import com.tedu.employeemanager.entity.Employee;

import java.util.List;

public class QuertActivity extends AppCompatActivity {

    private ListView listView;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quert);
        initUI();
        asyncqQuertEmployee();
    }

    private void initUI() {

        listView = (ListView) findViewById(R.id.lv_quert);
        adapter = new EmployeeAdapter(QuertActivity.this);
        listView.setAdapter(adapter);
    }

    private void asyncqQuertEmployee() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Employee> list = HttpManager.quertDatasGet();
                if (list!=null) {
                    Message message = handler.obtainMessage();
                    message.obj = list;
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<Employee> list = (List<Employee>) msg.obj;
                if (list != null) {
                    adapter.addEmployee(list);
                }
            }
        }
    };

    public void quert(View view) {
        Intent intent = new Intent(QuertActivity.this, AddActivity.class);
        startActivity(intent);
    }
}

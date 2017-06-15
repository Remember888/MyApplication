package com.tedu.employeemanager;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tedu.employeemanager.Manager.HttpManager;
import com.tedu.employeemanager.entity.Employee;

public class AddActivity extends AppCompatActivity {

    private EditText et_name_add;

    private EditText et_salary_add;
    private EditText et_age_add;
    private RadioButton rb_man;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initUI();
    }

    private void initUI() {
        et_name_add = (EditText) findViewById(R.id.et_name_add);
        et_salary_add = (EditText) findViewById(R.id.et_salary_add);
        et_age_add = (EditText) findViewById(R.id.et_age_add);
        rb_man = (RadioButton) findViewById(R.id.rb_01_add);

    }

    public void add(View view) {
        String name = et_name_add.getText().toString();
        int age = Integer.parseInt(et_age_add.getText().toString());
        int salary = Integer.parseInt(et_salary_add.getText().toString());
        String gender = "";
        if (rb_man.isChecked()) {
            gender = "m";
        } else {
            gender = "f";
        }
        Employee employee = new Employee(-1,name, age, salary, gender);
        System.out.println(employee);

        asyncAddEmployee(employee);
    }

    private void asyncAddEmployee(final Employee employee) {
        new Thread(new Runnable() {
           @Override
           public void run() {
               boolean flag = HttpManager.employeeHttpPost(employee);
               Message message = handler.obtainMessage();
               message.what = 1;
               message.obj = flag;
               handler.sendMessage(message);
           }
       }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Boolean flag = (Boolean) msg.obj;
                if (flag) {
                    Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}

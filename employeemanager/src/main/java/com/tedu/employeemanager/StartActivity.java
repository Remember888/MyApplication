package com.tedu.employeemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_regist;
    private Button btn_add;
    private Button btn_quert;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initUI();
    }

    private void initUI() {
        btn_regist = (Button) findViewById(R.id.btn_regist_start);
        btn_add = (Button) findViewById(R.id.btn_add_start);
        btn_quert = (Button) findViewById(R.id.btn_quert_start);
        btn_login = (Button) findViewById(R.id.btn_login_start);

        btn_add.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        btn_quert.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_start:
                Intent intent = new Intent(StartActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_regist_start:
                Intent intent1 = new Intent(StartActivity.this, RegistActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_quert_start:
                Intent intent2 = new Intent(StartActivity.this, QuertActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_login_start:
                Intent intent3 = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}

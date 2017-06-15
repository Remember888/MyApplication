package com.tedu.employeemanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tedu.employeemanager.Manager.HttpManager;
import com.tedu.employeemanager.entity.User;

public class RegistActivity extends AppCompatActivity {

    private EditText et_loginname;
    private EditText et_password_regist;
    private EditText et_ensure_password_regist;
    private EditText et_realname_regist;
    private EditText et_email_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initUI();



    }

    private void initUI() {
        et_loginname = (EditText) findViewById(R.id.et_localname_regist);
        et_password_regist = (EditText) findViewById(R.id.et_password_regist);
        et_ensure_password_regist = (EditText) findViewById(R.id.et_ensure_password_regist);
        et_realname_regist = (EditText) findViewById(R.id.et_realname_regist);
        et_email_regist = (EditText) findViewById(R.id.et_email_regist);
        Button btn_regist = (Button) findViewById(R.id.btn_regist);
    }

    public void register(View view) {
        String loginname = et_loginname.getText().toString();
        String password = et_password_regist.getText().toString();
        String ensure_password = et_ensure_password_regist.getText().toString();
        String realname = et_realname_regist.getText().toString();
        String email = et_email_regist.getText().toString();

        if (!password.equals(ensure_password)) {
            Toast.makeText(RegistActivity.this, "你所输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(loginname)||
                TextUtils.isEmpty(loginname)|| TextUtils.isEmpty(password)||
                TextUtils.isEmpty(ensure_password)|| TextUtils.isEmpty(realname)||
                TextUtils.isEmpty(email)){
            Toast.makeText(RegistActivity.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
            return;
        };
        User user = new User(-1, loginname, password, realname, email);
        asnyRegist(user);

    }

    private void asnyRegist(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.RegistManager(user);
                Message message = handler.obtainMessage();
                message.obj = flag;
                handler.sendMessage(message);
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean flag = (Boolean) msg.obj;
            if (flag) {
                Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                cleanContent();
            } else {
                Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void cleanContent() {
        et_loginname.setText("");
        et_password_regist.setText("");
        et_email_regist.setText("");
        et_realname_regist.setText("");
        et_ensure_password_regist.setText("");

    }
}

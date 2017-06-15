package com.tedu.employeemanager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tedu.employeemanager.Manager.HttpManager;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private EditText et_localname_login;
    private EditText et_password_login;
    private EditText et_code_login;
    private ImageView ig_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        asnyBitmap();
    }

    private void initUI() {
        et_localname_login = (EditText) findViewById(R.id.et_localname_login);
        et_password_login = (EditText) findViewById(R.id.et_password_login);
        et_code_login = (EditText) findViewById(R.id.et_code_login);
        ig_code =  (ImageView) findViewById(R.id.iv_code_login);
    }

    public void login(View view) {
        String login = et_localname_login.getText().toString();
        String password = et_password_login.getText().toString();
        String code = et_code_login.getText().toString();
        asyncLogin(login, password, code);
    }

    public void  asyncbitmap(View view) throws IOException {
        asnyBitmap();
    }

    private void asnyBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Bitmap bitmap = HttpManager.LoginCodeHttpGet();
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void asyncLogin(final String loginname,final String password,final String code)  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                try {
                    flag = HttpManager.LoginCodeHttpPost(loginname, password, code);
                    Message message = handler.obtainMessage();
                    message.what = 2;
                    message.obj = flag;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ig_code.setImageBitmap(bitmap);
                    break;
                case 2:
                    Boolean flag = (Boolean) msg.obj;
                    if (flag) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
}

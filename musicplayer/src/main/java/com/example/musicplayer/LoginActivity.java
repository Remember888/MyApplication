package com.example.musicplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.musicplayer.R.id.et_call;

public class LoginActivity extends AppCompatActivity {
    private ImageView ivCall;
    private ImageView ivError;
    private ImageView ivPassage;
    private CheckBox cbView;
    private CheckBox cbCheck;
    private Button btnlocal;
    private EditText edcall;
    private EditText edpassage;
    private SharedPreferences prefer;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edcall = (EditText) findViewById(et_call);
        edpassage = (EditText) findViewById(R.id.et_passage);
        ivError = (ImageView) findViewById(R.id.iv_error);
        cbView = (CheckBox) findViewById(R.id.cb_passage);
        cbCheck = (CheckBox) findViewById(R.id.cb_check);
        edcall = (EditText) findViewById(et_call);
        edpassage = (EditText) findViewById(R.id.et_passage);
        btnlocal = (Button) findViewById(R.id.btn_login);
        cbCheck = (CheckBox) findViewById(R.id.cb_check);

        /**
         * 当点击按钮时所发生的事件
         */
        btnlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                local();
                isCheck();
                editor = prefer.edit();
                String user = edcall.getText().toString();
                String passage = edpassage.getText().toString();
                if (cbCheck.isChecked()) {
                    editor.putBoolean("ischeck",true);
                    editor.putString("user", user);
                    editor.putString("passage", passage);
                    editor.commit();
                } else {
                    editor.clear();
                }
            }


        });

        checkedit();
        setvisible();

    }

    /**
     * 判断用户是否输入用户名，若未输入，则右侧的图标不显示
     */
    private void setvisible() {
        String user = edcall.getText().toString();
        edcall.addTextChangedListener(new BastTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() > 0) {
                    ivError.setVisibility(View.VISIBLE);
                } else {
                    ivError.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 当点击右侧的眼时显示密码
     */
    private void checkedit() {
        String user = edcall.getText().toString();
        cbView = (CheckBox) findViewById(R.id.cb_passage);
        cbView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edpassage.setInputType((isChecked) ? 0x91 : 0x81);
            }
        });
    }


    /**
     * 判断当点击按钮时用户是否输出错误
     */
    public void local() {
        String user = edcall.getText().toString();
        String passage = edpassage.getText().toString();
        if (user.isEmpty()) {
            edcall.setError("用户名为空");
        }
        if (passage.isEmpty()) {
            edpassage.setError("密码为空");
        }
        if (user.equals("1") && passage.equals("1")) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "你所输入的用户名或密码错误", Toast.LENGTH_LONG).show();
        }
    }

    public void isCheck() {
        prefer = PreferenceManager.getDefaultSharedPreferences(this);

        boolean istrue = prefer.getBoolean("ischeck", false);
        if (istrue) {
            String user = prefer.getString("user", "");
            String passage = prefer.getString("passage","");
            edcall.setText(user);
            edcall.setText(user);
            edpassage.setText(passage);
        }

    }
}


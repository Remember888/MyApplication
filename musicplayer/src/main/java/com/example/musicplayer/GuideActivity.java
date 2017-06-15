package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public class GuideActivity extends Activity implements View.OnClickListener {
    private Button btn_user;
    private Button btn_visitor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        getLocalContent();
        getMainContent();
    }

    private void getMainContent() {
        btn_visitor = (Button) findViewById(R.id.btn_user);
        btn_visitor.setOnClickListener(this);
    }

    private void getLocalContent() {
        btn_visitor = (Button) findViewById(R.id.btn_tourist);
        btn_visitor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tourist:
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user:
                Intent intent1 = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}

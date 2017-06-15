package com.example.day18;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup=(RadioGroup) findViewById(R.id.rGroupId);
        //添加监听
        radioGroup.setOnCheckedChangeListener(this);
        RadioButton radioButton= (RadioButton) radioGroup.findViewById(R.id.radio0);
        //让第0个默认选中
        radioButton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //1.获得fragmentmanager
        FragmentManager fragmentManager=
        getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction fragmentTransaction=
        fragmentManager.beginTransaction();
        //3.替换fragment
        switch (checkedId){
            case R.id.radio0:
                fragmentTransaction.replace(R.id.containerId,new Fragment01(),"tag01");
                 break;
            case R.id.radio1:
                 fragmentTransaction.replace(R.id.containerId,new Fragment02(),"tag02");
                 break;
        }
        //4.提交事务
        fragmentTransaction.commit();

    }
}

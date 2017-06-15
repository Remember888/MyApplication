package com.example.contactsbook;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.contactsbook.Adatper.MyFragmentPagerAdapter;
import com.example.contactsbook.fragment.Radio_Four_Fragment;
import com.example.contactsbook.fragment.Radio_One_Fragment;
import com.example.contactsbook.fragment.Radio_Three_Fragment;
import com.example.contactsbook.fragment.Radio_Two_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<String> list = new ArrayList<>();
    private RadioButton radioButton;
    private MyFragmentPagerAdapter adpter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniViewPager();
        radioGroupSeletor();

    }

    //RadioGroup的初始化和RadioButton的选择
    private void radioGroupSeletor() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) group.findViewById(checkedId);
                int count = group.indexOfChild(radioButton);
                viewPager.setCurrentItem(count);
            }
        });
        radioButton = (RadioButton) radioGroup.findViewById(R.id.rb_01);
        radioButton.setChecked(true);
    }

    //Viewpager的初始化
    private void iniViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        adpter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adpter);
        adpter.addFragment(new Radio_One_Fragment());
        adpter.addFragment(new Radio_Two_Fragment());
        adpter.addFragment(new Radio_Three_Fragment());
        adpter.addFragment(new Radio_Four_Fragment());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (radioGroup != null) {
                    radioButton = (RadioButton) radioGroup.getChildAt(position);
                    radioButton.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}

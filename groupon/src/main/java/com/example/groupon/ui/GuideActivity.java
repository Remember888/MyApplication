package com.example.groupon.ui;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.groupon.R;
import com.example.groupon.fragment.Framgent_four;
import com.example.groupon.fragment.Framgent_one;
import com.example.groupon.fragment.Framgent_three;
import com.example.groupon.fragment.Framgent_two;
import com.example.groupon.view.CircleImageView;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class GuideActivity extends FragmentActivity {

    private ViewPager viewPager;
    private LinePageIndicator indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initUI();
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        FragmentPagerAdapter adapter =   new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator = (LinePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(0xFFFFFF);
        indicator.setStrokeWidth(2 * density);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    indicator.setVisibility(View.INVISIBLE);
                } else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdapter extends FragmentPagerAdapter{
        private List<Fragment> framents = new ArrayList<>();
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            framents.add(new Framgent_one());
            framents.add(new Framgent_two());
            framents.add(new Framgent_three());
            framents.add(new Framgent_four());
        }

        @Override
        public Fragment getItem(int position) {
            return framents.get(position);
        }

        @Override
        public int getCount() {
            return framents.size();
        }
    }
}

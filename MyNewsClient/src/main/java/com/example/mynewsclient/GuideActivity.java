package com.example.mynewsclient;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewpager;
    private MyAdapter adapter;
    private Button button;
    private int[] arr = {R.drawable.guide_one,R.drawable.guide_two,R.drawable.guide_three};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        viewpager = (ViewPager) findViewById(R.id.pager_guide);
        adapter = new MyAdapter();
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(this);
        button = (Button) findViewById(R.id.btn_guide);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        private ImageView imageView;
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            imageView = new ImageView(container.getContext());
            imageView.setImageResource(arr[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((ImageView) object);
        }
    }




    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        if (arg0 == arr.length - 1) {
            button.setVisibility(View.VISIBLE);
        }else {
            button.setVisibility(View.GONE);
        }
    }
    }


package com.example.viewpager2;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TextView> list;
    private ViewPager pager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        for (int i = 1; i < 4;i++) {
            TextView textView = new TextView(this);
            textView.setText("dfd"+i);
            textView.setTextSize(30);
            list.add(textView);
        }
        pager = (ViewPager) findViewById(R.id.vp_pager);
        PagerAdapter adapter = new MyAdapter();
        pager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tl_layout);
        tabLayout.setupWithViewPager(pager);

    }

    class MyAdapter extends PagerAdapter {
        String name[] = {"推荐", "军事", "功能"};
        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == list.get((Integer) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = list.get(position);
            container.addView(textView);
            return position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get((Integer) object));
        }
    }
}

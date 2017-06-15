package com.example.clickcleiditor;

import android.sax.TextElementListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ClickcleIdtor clickcleIdtor;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click);
        setClickcle();
        setViewPager();

    }

    private void setClickcle() {
        clickcleIdtor = (ClickcleIdtor) findViewById(R.id.cctor);
    }

    private void setViewPager() {
        pager = (ViewPager) findViewById(R.id.vipager);
        PagerAdapter adapter = new InnerAdapter();
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clickcleIdtor.upDateFillPos(position, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class InnerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView text = new TextView(container.getContext());
           text.setText("pager" + position);
           container.addView(text);
            return text;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((TextView) object);
        }
    }
}

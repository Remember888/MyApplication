package com.example.day08;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCircleIndicatior();
        setViewPager();
    }

    private void setCircleIndicatior(){
        circleIndicator = (CircleIndicator) findViewById(R.id.circleViewId);

    }
    private void setViewPager(){
        ViewPager vPager= (ViewPager) findViewById(R.id.vPagerId);
        PagerAdapter adapter=new InnerPagerAdapter();
        vPager.setAdapter(adapter);
        vPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       // circleIndicator.updateFillCirclePos(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        circleIndicator.updateFillCirclePos(position,0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class InnerPagerAdapter extends  PagerAdapter{

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView=new TextView(container.getContext());
            textView.setTextSize(30);
            textView.setText("Pager-"+position);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((TextView)object);
        }
    }
}

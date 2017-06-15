package com.example.pager;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private Integer[] arr = {R.mipmap.banner01, R.mipmap.banner02, R.mipmap.banner03
    };
    private ViewPager viewPager;
    private RadioGroup rgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        PagerAdapter adapter = new ImagePager();
        viewPager.setAdapter(adapter);
        rgroup = (RadioGroup) findViewById(R.id.rg_group);
        rgroup.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton button = (RadioButton) group.findViewById(checkedId);
        int position = group.indexOfChild(button);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (rgroup != null) {
            RadioButton button = (RadioButton) rgroup.getChildAt(position);
            button.setChecked(true) ;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ImagePager extends PagerAdapter{

        @Override
        public int getCount() {
            return arr.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            int image = arr[position];
            view.setImageResource(image);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}

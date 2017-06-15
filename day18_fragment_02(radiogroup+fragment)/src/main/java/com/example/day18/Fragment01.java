package com.example.day18;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment01 extends Fragment {


    public Fragment01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_01, container, false);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.vpagerId);
        FragmentPagerAdapter adapter=
                //当fragment中再次嵌套fragment时可以使用getChildFragmentManager
                new ChildFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        return view;
    }
    //FragmentPagerAdapter 继承pagerAdapter
    class ChildFragmentAdapter extends FragmentPagerAdapter{

        public ChildFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        /**根据位置position返回position,此方法在初始化
         * item的instantiateItem这个方法中进行调用*/
        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new ChildFragment01();
            }else if(position==1){
                return new ChildFragment02();
            }
            return null;
        }
        /**此方法用户返回viewpager中item的个数(
         * 这个item现在是fragment)*/
        @Override
        public int getCount() {
            return 2;
        }
    }

}

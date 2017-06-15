package com.example.contactsbook.Adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.example.contactsbook.fragment.Radio_Four_Fragment;
import com.example.contactsbook.fragment.Radio_One_Fragment;
import com.example.contactsbook.fragment.Radio_Three_Fragment;
import com.example.contactsbook.fragment.Radio_Two_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<Fragment> list = new ArrayList<>();

    public void addFragment(Fragment fragment){
        if (fragment != null) {
            list.add(fragment);
            notifyDataSetChanged();
        }
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new Radio_One_Fragment();
            case 1:
                return  new Radio_Two_Fragment();
            case 2:
                return  new Radio_Three_Fragment();
            case 3:
                return  new Radio_Four_Fragment();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        list.remove(object);
    }
}

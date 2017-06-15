package com.example.groupon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.groupon.R;
import com.example.groupon.util.Btn_Skip_Util;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class Framgent_three extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_guide_three, container, false);
        Button button = (Button) view.findViewById(R.id.btn_skipC);
        Btn_Skip_Util.setClick(button, getActivity());
        return view;
    }



}


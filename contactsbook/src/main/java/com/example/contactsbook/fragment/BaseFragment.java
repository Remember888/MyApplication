package com.example.contactsbook.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.contactsbook.R;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public abstract class BaseFragment extends Fragment {


   private LinearLayout linearLayout;
    private ImageView leftImage;
    private TextView centerTitle;
    private ImageView rightImage;

    public void iniFragmentTitle(int leftId, String title, int rightId,View view) {
        if (view == null) {
            return;
        }
        leftImage = (ImageView) view.findViewById(R.id.iv1_title);
        centerTitle = (TextView) view.findViewById(R.id.tv_title);
        rightImage = (ImageView) view.findViewById(R.id.iv2_title);

        if (leftId <= 0) {
            leftImage.setVisibility(View.INVISIBLE);
        } else {
            leftImage.setImageResource(leftId);
        }
        if (title == null) {
            centerTitle.setVisibility(View.INVISIBLE);
        } else {
            centerTitle.setText(title);
        }
        if (rightId <= 0) {
            rightImage.setVisibility(View.INVISIBLE);
        } else {
            rightImage.setImageResource(rightId);
        }
    }
    public abstract void titleUI();
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>{
        class Holder extends RecyclerView.ViewHolder {

            public Holder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
    }



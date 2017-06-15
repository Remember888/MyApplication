package com.example.newsfragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.newsfragmenttest.R.id.ll_news_content;

/**
 * Created by Administrator on 2017/4/16 0016.
 * 该代码块的作用：
 * 用于将新闻的标题和内容显示到页面上
 */

public class News_content_fragment extends Fragment {
    @Nullable
    private View view;
    private TextView title;
    private TextView content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_flag, container, false);
        return view;
    }

    /**
     * 显示页面中的标题和内容
     * @param newsTitle
     * @param newsContent
     */
    public void refresh(String newsTitle, String newsContent) {
        title = (TextView) view.findViewById(R.id.tv_title);
        content = (TextView) view.findViewById(R.id.tv_content);
        View v = view.findViewById(ll_news_content);
        v.setVisibility(View.VISIBLE);
        title.setText(newsTitle);
        content.setText(newsContent);
    }
}

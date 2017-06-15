package com.example.newsfragmenttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/4/16 0016.
 */

public class NewsActivity extends Activity {
    /**
     * 作用：从外部将参数传进来
     * @param context
     * @param newsTitle
     * @param newsContent
     */
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra("newstitle", newsTitle);
        intent.putExtra("newscontent", newsContent);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_flag);
        String newsTitle = getIntent().getStringExtra("newstitle");
        String newsContent = getIntent().getStringExtra("newscontent");
        News_content_fragment frag = (News_content_fragment) getFragmentManager().findFragmentById(R.id.frag_content);
        frag.refresh(newsTitle, newsContent);
    }
}

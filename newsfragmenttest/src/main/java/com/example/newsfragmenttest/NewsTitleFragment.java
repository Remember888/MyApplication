package com.example.newsfragmenttest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/16 0016.
 */

public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<News> list;
    private NewsAdapter adapter ;
    private View view;
    private ListView listView;
    private boolean isTwoPane;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        list = getNews();
        adapter = new NewsAdapter(context, R.layout.news_item, list);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_list, container, false);
        listView = (ListView) view.findViewById(R.id.lv_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ((getActivity().findViewById(R.id.news_frag_layout)) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news = list.get(position);
        if (isTwoPane) {
            News_content_fragment fragment = (News_content_fragment) getFragmentManager().findFragmentById(R.id.frag_content);
            fragment.refresh(news.getTitle(), news.getContent());
        } else {
            NewsActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
        }

    }
    private List<News> getNews(){
        List<News> list = new ArrayList<>();
        News news = new News();
        news.setTitle("Succeed in College as a learning Disabled Student");
        news.setContent("College frashmen will soon learn to live with a roommate");
        list.add(news);
        return list;
    }
}

package com.example.recycleviewrefresh;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerId);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeId);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        loadRecycle();
    }

    private void loadRecycle() {
        new LoadAsyncTask(swipeRefreshLayout).execute();
    }

    @Override
    public void onRefresh() {
        loadRecycle();
    }

    static class LoadAsyncTask extends AsyncTask<String, Void, List<String>> {
        private WeakReference<SwipeRefreshLayout> weakReference;

        public LoadAsyncTask(SwipeRefreshLayout swipeRefreshLayout) {
            weakReference = new WeakReference<SwipeRefreshLayout>(swipeRefreshLayout);
        }
        @Override
        protected List<String> doInBackground(String... params) {
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<>();
            String data = "A/B/C/D/E/F/G";
            list.addAll(Arrays.asList(data.split("/")));
            return list;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            SwipeRefreshLayout swipeRefreshLayout = weakReference.get();
            if (swipeRefreshLayout == null) return;
            RecyclerView recyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.recyclerId);
            MyAdapter myAdapter = (MyAdapter) recyclerView.getAdapter();
            myAdapter.addAll(0,strings);
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

            Log.d("MainActivity", strings.toString());
        }
    }
}

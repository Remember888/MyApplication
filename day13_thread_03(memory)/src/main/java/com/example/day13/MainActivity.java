package com.example.day13;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mObjects=new ArrayList<>();
    private SimpleRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleRecyclerAdapter(this,mObjects);
        recyclerView.setAdapter(adapter);
        loadObjectAsync();
    }
    public void loadObjectAsync(){
        new LoadAsyncTask(recyclerView).execute();
    }
    static class LoadAsyncTask extends AsyncTask<String,Void,List<String>>{

     /* //强引用(直接引用)
        private RecyclerView mRecyclerView;
        public LoadAsyncTask(RecyclerView recyclerView){
            mRecyclerView=recyclerView;
        }*/
        /**弱引用WeakReference引用的对象允许被销毁?
         * 为什么对view的引用应该是弱引用呢？
         * 因为我们可能要销毁activity(view关联着activity)
         * */
        private WeakReference<RecyclerView> weakReference;
        public LoadAsyncTask(RecyclerView recyclerView){
            weakReference=new WeakReference<RecyclerView>(recyclerView);
        }
        @Override
        protected List<String> doInBackground(String... params) {
            try{Thread.sleep(5000);}catch(Exception e){}
            List<String> objects=new ArrayList<>();
            String dataStr="A/B/C/D/E/F/G";
            objects.addAll(Arrays.asList(dataStr.split("/")));
            return objects;
        }
        /**此方法运行在主线程*/
        @Override
        protected void onPostExecute(List<String> strings) {
            RecyclerView mRecyclerView=weakReference.get();
            if(mRecyclerView==null)return;
            SimpleRecyclerAdapter adapter=
                    (SimpleRecyclerAdapter)
                            mRecyclerView.getAdapter();

            //添加数据
            adapter.addAll(strings);
            //刷新页面(应在主线程执行)
            adapter.notifyDataSetChanged();
            Log.i("TAG","onPostExecute.strings="+strings.toString());
        }
    }
}

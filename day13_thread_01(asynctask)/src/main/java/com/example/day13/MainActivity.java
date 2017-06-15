package com.example.day13;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick01(View v){
        //1创建异步任务
        DownAsyncTask task=new DownAsyncTask(v);
        //2启动异步任务
        //task.execute();//顺序执行
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void onClick02(View v){
        //1创建异步任务
        DownAsyncTask task=new DownAsyncTask(v);
        //2启动异步任务
        //task.execute();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    class DownAsyncTask extends AsyncTask<Void,Integer,String> {
        private View mView;
        public DownAsyncTask(View view){
             mView=view;
        }

        /**此方法运行与主线程，在doInbackGround方法之前执行，一般用于初始化*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置view不可点击
            mView.setEnabled(false);
        }

        //此方法运行在工作线程(此方法在父类中是抽象方法)
        @Override
        protected String doInBackground(Void... params) {
            //模拟下载过程
            for(int i=0;i<=100;i++){
              try{Thread.sleep(100);}catch (Exception e){}
                //此方法的作用主要用于发布进度
                //进度发布以后会由底层系统调用onProgressUpdate
                publishProgress(i);
            }
            return "down-ok";
        }//void

        /**此方法运行于主线程，用于更新进度*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MyButton myBtn=(MyButton) mView;
            myBtn.setProgress(values[0]*1.0f/100);
            myBtn.invalidate();
            myBtn.setText(values[0]+"%");
        }

        /**此方法运行于主线程，用于处理doInbackGround方法的返回结果*/
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(mView!=null&&mView instanceof Button){
                ((Button) mView).setText(result);
            }
        }
    }
}

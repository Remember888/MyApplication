package com.example.download;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyButton myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick01(View view) {
        DownAsynTask task = new DownAsynTask(view);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void onClick02(View view) {
        DownAsynTask task = new DownAsynTask(view);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}

class DownAsynTask extends AsyncTask<Void, Integer, String> {

    private View v;

    public DownAsynTask(View view) {
        v = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        v.setEnabled(false);
    }

    @Override
    protected String doInBackground(Void... params) {
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return "task_OK";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        MyButton myButton = (MyButton) v;
        myButton.setText(values[0] + "%");
        myButton.setTextColor(Color.BLUE);
        myButton.setPregress(values[0] * 1.0f / 100);
        myButton.invalidate();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (v != null && v instanceof MyButton) {
            ((MyButton) v).setText(s);
        }
    }
}

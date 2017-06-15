package com.example.httpurlconnection;

import android.icu.text.StringSearch;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.StringCharacterIterator;

public class MainActivity extends AppCompatActivity {
    public static final int SEND_RESPONSE = 0;
    private HttpURLConnection connection;
    private  Button button;
    private TextView text;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_RESPONSE:
                    String data = (String) msg.obj;
                    text.setText(data);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.tv_view);
        button = (Button) findViewById(R.id.btn_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_send) {
                    sendHttpURLConnectionWithTextView();
                }
            }
        });

    }

    private void sendHttpURLConnectionWithTextView() {
        //开启线程发起网络请求

        new Thread(new Runnable() {
            String s;
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.baidu.com");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    //请求成功
                    if(connection.getResponseCode() == 200) {
                        InputStream input = conn.getInputStream();

                        BufferedInputStream bis = new BufferedInputStream(input);
                        int len = -1;
                        byte[] buf = new byte[1024*1024];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while((len = bis.read(buf)) != -1) {
                            baos.write(buf,0,len);
                            baos.flush();
                        }
                        String content = baos.toString();
                        Message message = new Message();
                        message.what = SEND_RESPONSE;

                        message.obj  = content;
                        handler.sendMessage(message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (connection != null) {
                        connection.disconnect();

                    }
                    parseXMLWithPull(s);
                }
            }
        }).start();
    }
    private void parseXMLWithPull(String s) {

    }
}

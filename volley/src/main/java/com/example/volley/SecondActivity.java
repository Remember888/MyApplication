package com.example.volley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class SecondActivity extends AppCompatActivity {

    private RequestQueue queue;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);


    }

    public void getResponse(View view) {

                XMLRequest request = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml",
                        new Response.Listener<XmlPullParser>() {
                            @Override
                            public void onResponse(XmlPullParser parser) {
                                try {

                                    int eventType = parser.getEventType();
                                    while (eventType != XmlPullParser.END_DOCUMENT) {
                                        switch (eventType) {

                                            case XmlPullParser.START_TAG:
                                                String name = parser.getName();
                                                if (name.equals("city")) {
                                                    String cityName = parser.getAttributeValue(1);
                                                    Log.d("LAG", cityName);
                                                }
                                                break;
                                        }
                                            eventType = parser.next();
                                    }

                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LAG:error", error.getMessage());
                    }
                });
                queue.add(request);
            }

}

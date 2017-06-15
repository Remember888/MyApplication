package com.example.weatherreport.manager;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherreport.MainActivity;
import com.example.weatherreport.constand.IURL;
import com.example.weatherreport.entre.Weather;
import com.google.gson.Gson;

import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class HttpManager{

    public static WeatherLoadListener mlistener;
    private static RequestQueue queue = null;

    public static void localWeather(Context context, String  cityname, final WeatherLoadListener listener){
        try {
            cityname = URLEncoder.encode(cityname, "UTF-8");
            String url= IURL.ROOT+"cityname="+cityname+"&key="+IURL.LOCAL_JSON;
            String url1 = IURL.ROOT1;
            if (queue == null) {
                queue = Volley.newRequestQueue(context);
            }
            StringRequest request = new StringRequest(url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    Weather weather = gson.fromJson(s, Weather.class);
                    listener.onWeatherLoad(weather);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("LAG:Error", volleyError.getMessage());
                }
            });
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public interface WeatherLoadListener{
        void onWeatherLoad(Weather  mweather);
    }
}

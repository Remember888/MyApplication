package com.example.weatherreport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherreport.R;
import com.example.weatherreport.entre.Weather;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private Context mcontext;
    private List<Weather.ResultBean.DataBean.WeatherBeanX> mlist=new ArrayList<>();

    public MyAdapter(Context context){
        mcontext = context;
    }
    public void addWeather(List<Weather.ResultBean.DataBean.WeatherBeanX> list,Boolean isClean){
        if (isClean) {
            mlist.clear();
            mlist.addAll(list);
        } else {
            mlist.addAll(list);
        }
        notifyDataSetChanged();

    }
    class Holder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text1;
        private TextView text2;
        private TextView text3;
        public Holder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.iv_list_weather);
            text1 = (TextView) v.findViewById(R.id.tv_list_weather);
            text2 = (TextView) v.findViewById(R.id.tv_list_temp);
            text3 = (TextView) v.findViewById(R.id.tv_list_week);
        }
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mcontext).inflate(R.layout.fragment_listview_child,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Weather.ResultBean.DataBean.WeatherBeanX weathers = mlist.get(position);
        String number = weathers.getInfo().getDay().get(0);
        Map<String, Integer> map = new HashMap<>();
        map.put("0", R.drawable.sunny);
        map.put("1", R.drawable.cloudy);
        map.put("2", R.drawable.lotcloudy);
        map.put("3", R.drawable.rain);
        holder.image.setImageResource(map.get(number));
        holder.text1.setText(weathers.getInfo().getDay().get(1));
        String dataTemp = weathers.getInfo().getDay().get(2);
        String nightTemp = weathers.getInfo().getNight().get(2);
        holder.text2.setText(nightTemp + "C" +"~"+ dataTemp + "C");
        holder.text3.setText("星期"+weathers.getWeek());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

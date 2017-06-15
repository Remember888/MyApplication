package com.example.weatherreport;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherreport.adapter.MyAdapter;
import com.example.weatherreport.entre.Weather;
import com.example.weatherreport.manager.HttpManager;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private boolean isStar = false;
    @ViewInject(R.id.swipe_main)
    SwipeRefreshLayout swipe;
     @ViewInject(R.id.dl_main)
     DrawerLayout dl_main;
    @ViewInject(R.id.ng_main)
    NavigationView ng_main;
    @ViewInject(R.id.iv_frag_side)
    ImageView iv_frag_side;
    @ViewInject(R.id.iv_frag_statics)
    ImageView iv_frag_statics;
    @ViewInject(R.id.tv_frag_city)
    TextView tv_frag_city;
    @ViewInject(R.id.tv_frag_update)
    TextView tv_frag_update;
    @ViewInject(R.id.tv_frag_air)
    TextView tv_frag_air;
    @ViewInject(R.id.tv_frag_week)
    TextView tv_frag_week;
    @ViewInject(R.id.ry_frag)
    RecyclerView ry_frag;
    @ViewInject(R.id.circle_weather)
    CircleSegmentBar progressBar;
    @ViewInject(R.id.fl_main)
    FrameLayout frameLayout;

    List<Weather.ResultBean.DataBean.WeatherBeanX> list = null;
    private MyAdapter myAdapter;
    private Handler handler;
    private Runnable runn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDrawLayout();
        initialRealTime();
    }
    private void initialRealTime() {
        progressBar.setCircleViewPadding(5);
        progressBar.setWidth(280);
        progressBar.setWidthProgressBackground(30);
        progressBar.setWidthProgressBarLine(25);
        progressBar.setStartPositionInDegrees(90);
        progressBar.setLinearGradientProgress(true);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initDrawLayout() {
        handler = new Handler();
        iv_frag_side.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        iv_frag_statics.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        iv_frag_statics.setOnClickListener(this);
        iv_frag_side.setOnClickListener(this);
        ng_main.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_01:
                        dl_main.closeDrawer(ng_main);
                        tv_frag_city.setText("北京");
                        break;
                    case R.id.item_02:
                        dl_main.closeDrawer(ng_main);
                        tv_frag_city.setText("上海");
                        break;
                    case R.id.item_03:
                        dl_main.closeDrawer(ng_main);
                        tv_frag_city.setText("深圳");
                        break;
                    case R.id.item_04:
                        dl_main.closeDrawer(ng_main);
                        tv_frag_city.setText("成都");
                        break;
                    case R.id.item_05:
                        dl_main.closeDrawer(ng_main);
                        tv_frag_city.setText("铁岭");
                        break;
                    case R.id.item_06:
                        dl_main.closeDrawer(ng_main);
                        break;
                        }
                return true;
            }
        });
        ry_frag.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myAdapter = new MyAdapter(MainActivity.this);
        ry_frag.setAdapter(myAdapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String cityName = tv_frag_city.getText().toString();
        reAsyncData(tv_frag_city.getText().toString(),true);
    }

    public void reAsyncData(String cityName, final boolean isClean) {
        HttpManager.localWeather(this, cityName, new HttpManager.WeatherLoadListener() {
            @Override
            public void onWeatherLoad(Weather mweather) {

                List<Weather.ResultBean.DataBean.WeatherBeanX> list = mweather.getResult().getData().getWeather();
                MainActivity.this.list = list;
                myAdapter.addWeather(list,isClean);
                String data = mweather.getResult().getData().getRealtime().getDate();
                String time = mweather.getResult().getData().getRealtime().getTime();
                tv_frag_update.setText(data+" "+time);
                String condition = mweather.getResult().getData().getRealtime().getWeather().getInfo();
                String quality = mweather.getResult().getData().getPm25().getPm25().getQuality();
                tv_frag_air.setText(condition + "|" + "空气质量为" + quality);
                int weekNo = mweather.getResult().getData().getRealtime().getWeek();
                switchWeek(weekNo);
                int temp = Integer.parseInt(mweather.getResult().getData().getRealtime().getWeather().getTemperature());
                setTemperature(temp);
            }
        });
    }

    private void switchWeek(int weekNo) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "星期一");
        map.put("2", "星期二");
        map.put("3", "星期三");
        map.put("4", "星期四");
        map.put("5", "星期五");
        map.put("6", "星期六");
        map.put("7", "星期日");
        tv_frag_week.setText(map.get(String.valueOf(weekNo)));
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_frag_side:
                if (dl_main.isDrawerOpen(GravityCompat.START)) {
                    dl_main.closeDrawer(ng_main);
                } else {
                    dl_main.openDrawer(ng_main);
                }
                break;
            case R.id.iv_frag_statics:
                int isVisi = frameLayout.getVisibility();
                if (isVisi==View.VISIBLE) {
                    frameLayout.setVisibility(View.INVISIBLE);
                } else{
                    frameLayout.setVisibility(View.VISIBLE);
                    GraphicalView view = ChartFactory.getLineChartView(this, getDataSet(), getRednderer());
                    frameLayout.addView(view);
                }
                break;
        }
    }


    public void setTemperature(final int temp) {
        handler.removeCallbacksAndMessages(null);
        runn = new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                progress++;
                if (progress < 100 * temp / 50) {
                    progressBar.setProgress((float) progress);
                    progressBar.setText(temp+"C");
                }
                handler.postDelayed(runn, 100);
            }
        };
        handler.postDelayed(runn, 1000);
    }
    public XYMultipleSeriesDataset getDataSet(){
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        String title[] = {"白天温度", "夜晚温度"};
        List<double[]> x = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            double[] doubles = {1, 2, 3, 4, 5, 6, 7};
            x.add(doubles);
        }
        List<double[]> y = new ArrayList<>();
        double[] day = new double[7];
        double[] night = new double[7];
        for (int i = 0;i < list.size();i++) {
            day[i] = Double.parseDouble(list.get(i).getInfo().getDay().get(2));
            night[i] = Double.parseDouble(list.get(i).getInfo().getNight().get(2));
        }
        y.add(day);
        y.add(night);
        for (int i = 0;i < title.length; i++) {
            XYSeries series = new XYSeries(title[i]);
            double[] xv = x.get(i);
            double[] yv = y.get(i);
            for (int j = 0; j < xv.length; j++) {
                series.add(xv[j], yv[j]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    public XYMultipleSeriesRenderer getRednderer(){
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int colors[] = new int[]{Color.GREEN, Color.BLUE};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.SQUARE};
        renderer.setXLabels(7);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setChartTitle(" 温度趋势");
        renderer.setXTitle("未来7天");
        renderer.setYTitle("温度");
        renderer.setChartTitleTextSize(48);
        renderer.setLabelsTextSize(30);
        renderer.setAxisTitleTextSize(30);
        renderer.setLegendTextSize(26);
        renderer.setYLabels(8);
        renderer.setXAxisMax(7.5);
        renderer.setYAxisMax(50);
        renderer.setXAxisMin(0.5);
        renderer.setYAxisMin(-15);
        renderer.setAxesColor(Color.LTGRAY);
        renderer.setLabelsColor(Color.LTGRAY);
        renderer.setPointSize(7f);
        renderer.setMargins(new int[]{50, 50, 15, 30});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setLineWidth(5);
            r.setColor(colors[i]);
            r.setFillPoints(true);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
}

package com.example.mynewsclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class MainActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_main);
        iniNavigationView(); //初始化 NavigationView
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        setToggle();

    }

    //初始化 NavigationView
    private void iniNavigationView() {
        NavigationView navigation = (NavigationView) findViewById(R.id.navigation_main);
        navigation.setItemIconTintList(null);

    }


    private void setToggle() {
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_main);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open_draw, R.string.close_draw);
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean b = super.onPrepareOptionsMenu(menu);
        toggle.syncState();
        return b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}

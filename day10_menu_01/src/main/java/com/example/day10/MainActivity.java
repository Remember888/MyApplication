package com.example.day10;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar=getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        //初始化drawerLayout
        setDrawerLayout();
    }
    /**初始化drawerLayout*/
    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout)
                findViewById(R.id.activity_main);
        //构建drawerLayout对象监听器
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
               R.string.open_drawer,
               R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("open");
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle("close");
            }
        };
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //添加drawerLayout监听器
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    /**在方法onCreateOptionsMenu之前执行此方法*/
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean flag=super.onPrepareOptionsMenu(menu);
        //同步drawerToggle状态
        drawerToggle.syncState();
        return flag;
    }*/

    /**用于创建选项菜单(activity创建时调用此方法)*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //解析res/main.xml菜单文件并初始化menu对象
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /**此方法用于处理选项菜单的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //openOrCloseDrawer();
        /*drawerToggle.onOptionsItemSelected(item);*/
        //获得菜单项id
        int id=item.getItemId();
        if(id==R.id.item01){
            Toast.makeText(this,item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if(id==R.id.item02){
            Toast.makeText(this,item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if(id==android.R.id.home){
            openOrCloseDrawer();
        }
        return super.onOptionsItemSelected(item);
    }
    /**打开或关闭DrawerLayout*/
        private void openOrCloseDrawer() {
            //判定drawerLayout是否是打开状态，
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                //假如是则关闭drawerLayout
                drawerLayout.closeDrawer(GravityCompat.START);
            }else{
                //假如不是则打开DrawerLayout
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
}

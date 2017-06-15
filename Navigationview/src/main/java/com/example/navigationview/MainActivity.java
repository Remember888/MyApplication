
package com.example.navigationview;

import android.content.ClipData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigation;
    private TextView text;
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setNevigationview();
        setToggle();

    }

    private void setToggle() {
        drawerLayout = (DrawerLayout) findViewById(R.id.DL_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_draw, R.string.close_draw){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
    }

    private void setNevigationview() {
        navigation = (NavigationView) findViewById(R.id.nv_view);
        text = (TextView) findViewById(R.id.tv_name);
        navigation.setItemIconTintList(null);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
//                        text.setText(item1.getTitle().toString());
                        text.setText("item1");
                        drawerLayout.closeDrawer(navigation);
                        break;
                    case R.id.item2:
//                        text.setText(item2.getTitle().toString());
                        text.setText("item2");
                        drawerLayout.closeDrawer(navigation);
                        break;
                    case R.id.item3:
                        text.setText("item3");
//                        text.setText(item3.getTitle().toString());
                        drawerLayout.closeDrawer(navigation);
                        break;
                    case R.id.item4:
                        text.setText("item4");
//                        text.setText(item4.getTitle().toString());
                        drawerLayout.closeDrawer(navigation);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itemmenu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean tt = super.onPrepareOptionsMenu(menu);
        toggle.syncState();
        return tt;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.phone:

                Toast.makeText(MainActivity.this, "phone", Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                Toast.makeText(MainActivity.this, "camena", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                //openOrDrawable();
        }
        return true;
    }

    private void openOrDrawable() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(navigation);
        } else {
            drawerLayout.openDrawer(navigation);
        }
    }
}

package com.example.datapicker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picker = (DatePicker) findViewById(R.id.dataId);
        Calendar calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int mouthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        picker.init(year, mouthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Toast.makeText(MainActivity.this,year+"/"+mouthOfYear+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("by", year + "/" + monthOfYear + "/" + dayOfMonth);
                setResult(200,intent);
                finish();
            }
        });
    }
}

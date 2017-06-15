package com.example.contactsbook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.contactsbook.Adatper.SMSAdapter;
import com.example.contactsbook.constant.Constant;
import com.example.contactsbook.entry.SMS;
import com.example.contactsbook.manager.SMSManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class CharActivity extends Activity {

    private static int threadId;
    private static ListView listView;
    private int position;
    private static SMSAdapter adapter = null;
    private ImageView image_left;
    private ImageView image_right;
    private TextView textView;
    private static String name;
    private static String address;
    private ImageButton ib_chat;
    private EditText editText;
    private Button button;
    static Context context=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        context=getApplicationContext();
        getThreadId();
        initListView();
    }

    private void initListView() {
        ib_chat = (ImageButton) findViewById(R.id.ib_chat);
        editText = (EditText) findViewById(R.id.et_chat);
        button = (Button) findViewById(R.id.btn_chat);

        image_left = (ImageView) findViewById(R.id.iv1_title);
        image_right = (ImageView) findViewById(R.id.iv2_title);
        textView = (TextView) findViewById(R.id.tv_title);
        image_left.setImageResource(R.drawable.ic_back);
        image_right.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(name)) {
            textView.setText(address);
        } else {
            textView.setText(name);

        }
        image_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.lv_chat);
        adapter=new SMSAdapter(this);
        listView.setAdapter(adapter);
        refreshSMS();


    }

    //刷新页面
    private static void refreshSMS() {
        //查询指定会话的短信
        List<SMS> smses = SMSManager.getAllSMS(context , threadId);
        adapter.addDatas(smses,true);
        //每次数据适配完成后显示最后一项
        listView.setSelection(
                adapter.getCount()-1);
    }
    public void send(View view){
        String content = editText.getText().toString();
        SMSManager.sendSMS(this, content, address);
        editText.setText("");

    }

    public void getThreadId() {
        Intent intent = getIntent();
        threadId = intent.getIntExtra("threadId",0);
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
    }

    public static class SMSReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constant.RECEIVED_SMS.equals(action)) {
                SMS sms = SMSManager.onReceiver(context, intent);
                if (sms.getAddress().equals(address)) {
                    SMSManager.saveReceiverSMS(context, threadId, sms);
                }
                refreshSMS();
            } else if (Constant.SEND_SMS.equals(action)) {
                String address = intent.getStringExtra("address");
                String body = intent.getStringExtra("body");

                SMSManager.saveSendSMS(context, address, body);

                refreshSMS();
            }
        }
    }


}

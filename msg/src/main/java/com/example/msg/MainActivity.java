package com.example.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_text;
    private ListView lv_list;
    private List<Msg> list = new ArrayList<>();
    private EditText edit;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsg();
        lv_list = (ListView) findViewById(R.id.lv_list);
        adapter = new MsgAdapter(this, R.layout.child_layout, list);
        lv_list.setAdapter(adapter);
        edit = (EditText) findViewById(R.id.et_text);
        btn_text = (Button) findViewById(R.id.btn_button);
        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String news = edit.getText().toString();
                if (!"".equals(news)) {
                    Msg msg = new Msg(news, Msg.SEND_MSG);
                    adapter.add(msg);
                    adapter.notifyDataSetChanged();
                    lv_list.setSelection(list.size());
                    edit.setText("");
                }
            }
        });
    }
    private void initMsg() {
        Msg msg = new Msg("Hello guy", Msg.SEND_MSG);
        list.add(msg);
        Msg msg1 = new Msg("Hello.Who is that?", Msg.RECEIVED_MSG);
        list.add(msg1);

    }
}


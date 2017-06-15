package com.example.contactsbook.fragment;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.contactsbook.Adatper.Radio_Four_Adapter;
import com.example.contactsbook.CharActivity;
import com.example.contactsbook.R;
import com.example.contactsbook.entry.Contact;
import com.example.contactsbook.entry.Conversation;
import com.example.contactsbook.entry.SMS;
import com.example.contactsbook.fragment.BaseFragment;
import com.example.contactsbook.manager.SMSManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Radio_Four_Fragment extends BaseFragment {
    private RelativeLayout layout;
    private View v;
    private Radio_Four_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.radio_four_frag,container,false);
        layout = (RelativeLayout) v.findViewById(R.id.actionbar_radio04);
        titleUI();
        initListView();
        setPermission();

        return v;
    }
    private void setPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECEIVE_SMS}, 200);
        } else {
            initListView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    initListView();

                }
                break;
        }
    }

    private void initListView() {
        ListView listView = (ListView) v.findViewById(R.id.lv_radio04);
        List<Conversation> list = SMSManager.getAllConversations(getActivity());
        adapter = new Radio_Four_Adapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversation = adapter.getItem(position);
                int threadId = conversation.get_id();
                String address = conversation.getAddress();
                String name = conversation.getName();
                Intent intent = new Intent(getActivity(), CharActivity.class);
                intent.putExtra("threadId", threadId);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }

    @Override
    public void titleUI() {
        iniFragmentTitle(-1,"短消息",-1,layout);
    }
}

package com.example.contactsbook.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.print.PrintJob;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.contactsbook.Adatper.Radio_One_Adapter;
import com.example.contactsbook.R;
import com.example.contactsbook.entry.Calllog;
import com.example.contactsbook.entry.Contact;
import com.example.contactsbook.fragment.BaseFragment;
import com.example.contactsbook.manager.ContactsManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Radio_One_Fragment extends BaseFragment {

    private RelativeLayout view;
    private ListView listView;
    private Radio_One_Adapter oneAdapter;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.radio_one_frag,container,false);
        view = (RelativeLayout) v.findViewById(R.id.actionbar_radio01);
        setPermistion();
        titleUI();
        initListView();

        return v;
    }

    private void initListView() {
        List<Calllog> list1 = ContactsManager.getPhotoByCalllog(getActivity());
        Log.d("LLD", list1.toString());
        listView = (ListView) v.findViewById(R.id.lv_radio01);
        oneAdapter = new Radio_One_Adapter(getActivity(),list1);
        listView.setAdapter(oneAdapter);
    }

    private void setPermistion() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALL_LOG},101);
        }else {
           initListView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                initListView();
            } else {
                Toast.makeText(getActivity(),"你没有访问权限",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        oneAdapter.notifyDataSetChanged();
    }


    @Override
    public void titleUI() {
        iniFragmentTitle(-1,"通话记录",-1,view);
    }
}

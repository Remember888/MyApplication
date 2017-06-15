package com.example.contactsbook.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.contactsbook.Adatper.Radio_Two_Adapter;
import com.example.contactsbook.R;
import com.example.contactsbook.entry.Contact;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.Radio_Two_Dialog_Manager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Radio_Two_Fragment extends BaseFragment {
    private RelativeLayout view;
    private GridView gridView;
    private ImageView imageTrue;
    private ImageView imageFalse;
    private EditText edit_linkman;
    private EditText edit_phone;
    private EditText edit_email;
    private EditText edit_contact_address;
    private EditText edit_company_address;
    private View dialogview;
    private Radio_Two_Adapter adapter;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.radio_two_frag,container,false);
        view = (RelativeLayout) v.findViewById(R.id.actionbar_radio02);

        setPermission();
        titleUI();
        initGridView();
        setGridViewClick(); //设置GridView的点击事件
        setGridViewLongClick();
        return v;
    }
    //设置权限
    private void setPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 102);
        } else {

            initGridView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initGridView();
            }
        }
    }

    private void initGridView() {
        List<Contact> list = ContactsManager.getAllContacts(getContext());
        Contact contact = new Contact(0, 0, "添加联系人", null, null, null);
        //将自定义的联系人插入到集合中作为集合的第一个元素
        list.add(0,contact);
        //将集合数据添加到适配器当中
        gridView = (GridView) v.findViewById(R.id.gv_radio02);
        adapter = new Radio_Two_Adapter(getActivity(),list);
        gridView.setAdapter(adapter);
    }


    //初始化标题栏
    private int picture = android.R.drawable.ic_menu_search;
    @Override
    public void titleUI() {
        iniFragmentTitle(-1," 联系人",picture,view);
    }

    // 初始化AlertDialog中所有的UI控件
    private void iniUI() {
        imageTrue = (ImageView)dialogview .findViewById(R.id .iv1_radio02_dialog);
        imageFalse = (ImageView) dialogview.findViewById(R.id.iv2_raido02_dialog);
        edit_linkman =  (EditText) dialogview.findViewById(R.id.et01_radio02_dialog);
        edit_phone = (EditText) dialogview.findViewById(R.id.et02_radio02_dialog);
        edit_email = (EditText) dialogview.findViewById(R.id.et03_radio02_dialog);
        edit_contact_address = (EditText) dialogview.findViewById(R.id.et04_radio02_dialog);
        edit_company_address = (EditText) dialogview.findViewById(R.id.et05_radio02_dialog);

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContact();
    }
    private void refreshContact() {
        //获联系人的信息
        List<Contact> contacts= ContactsManager.
                getAllContacts(getActivity());
        Contact contact = new Contact(0, 0, "添加联系人", null, null, null);
        //将自定义的联系人插入到集合中作为集合的第一个元素
        contacts.add(0,contact);
        //将集合数据添加到适配器当中
        adapter.addDatas(contacts,true);
    }

    //设置GridView的短按点击事件
    private void setGridViewClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //当点击第一个时，弹出对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    dialogview = View.inflate(getActivity(), R.layout.radio_two_dialog_add, null);
                    alert.setContentView(dialogview);
                    iniUI();

                    imageTrue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取EditView输入的信息
                            String linkman = edit_linkman.getText().toString();
                            String phone = edit_phone.getText().toString();
                            String email = edit_email.getText().toString();
                            String contact_address = edit_contact_address.getText().toString();
                            String company_address = edit_company_address.getText().toString();
                            if (TextUtils.isEmpty(linkman) || TextUtils.isEmpty(phone)) {
                                Toast.makeText(getActivity(), "联系人或电话号码不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT);
                                Uri uri = Uri.parse("tel:" + phone);
                                intent.setData(uri);
                                intent.putExtra(ContactsContract.Intents.Insert.NAME, linkman);
                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, contact_address);
                                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company_address);
                                startActivity(intent);
                                alert.dismiss();

                            }
                        }
                    });

                    imageFalse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                } else {
                    Contact contact =  adapter.getItem(position);
                    Radio_Two_Dialog_Manager manager = new Radio_Two_Dialog_Manager();
                    manager.showLinkMan(getActivity(), contact);
                }
        }
        });
    }

    //设置GridView的长按点击事件，实现删除功能
    private void setGridViewLongClick() {
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Contact contact = (Contact) adapter.getItem(position);
                    Radio_Two_Dialog_Manager.showAlectDialog(getActivity(),contact,adapter);
                }

                return true;
            }
        });
    }

}

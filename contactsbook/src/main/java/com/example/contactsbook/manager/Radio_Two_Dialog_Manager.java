package com.example.contactsbook.manager;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.contactsbook.Adatper.Radio_Two_Adapter;
import com.example.contactsbook.R;
import com.example.contactsbook.entry.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class Radio_Two_Dialog_Manager {

    private View view;
    private ImageView imager_left;
    private ImageView image_right;
    private ImageView image_head;
    private TextView text_phone;
    private ImageView image_phone;
    private TextView text_email;
    private ImageView image_email;
    private TextView text_address;
    private ImageView image_address;
    private AlertDialog alertDialog;

    public void showLinkMan(Context context, Contact contact) {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        view = View.inflate(context, R.layout.radio_two_dialog_head, null);
        alertDialog.setContentView(view);
        iniUI(context,contact);
    }
    //初始化所有的控件
    private void iniUI(final Context context, final Contact contact) {
        imager_left = (ImageView) view.findViewById(R.id.iv2_head_radio02_dialog);
        image_right = (ImageView) view.findViewById(R.id.iv1_head_radio02_dialog);
        image_head = (ImageView) view.findViewById(R.id.iv3_head_radio02_dialog);
        text_phone = (TextView) view.findViewById(R.id.tv_phone_radio02_dialog);
        image_phone = (ImageView) view.findViewById(R.id.iv_phone_radio02_dialog);
        text_email = (TextView) view.findViewById(R.id.tv_email_radio02_dialog);
        image_email = (ImageView) view.findViewById(R.id.iv_email_radio02_dialog);
        text_address = (TextView) view.findViewById(R.id.tv_address_radio02_dialog);
        image_address = (ImageView) view.findViewById(R.id.iv_address_radio02_dialog);

        text_phone.setText(contact.getPhone());
        String address = contact.getAddress();

        //判断地址和Email是否为空
        if (TextUtils.isEmpty(address)) {
            text_address.setText(address);
        } else {
            text_address.setText("地址为空");
        }
        String email = contact.getEmail();
        if (TextUtils.isEmpty(email)) {
            text_email.setText(email);
        } else {
            text_email.setText("Email为空");
        }

        //设置头像
        if (contact.getPhotoId() != 0) {
            Bitmap bitmap = ImageManager.formalBitmap(context, ContactsManager.getPhotoByPhotoId(context, contact.getPhotoId()));
            image_head.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_contact);
            Bitmap bitmap2 = ImageManager.formalBitmap(context, bitmap1);
            image_head.setImageBitmap(bitmap2);

        }

        //设置编辑点击事件
        imager_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_EDIT);
                Uri uri = Uri.parse("content://contacts/people/"+
                        contact.getId());
                intent.setData(uri);
                intent.putExtra("finishActivityOnSaveCompleted", true);
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });

        image_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



    }

    public static void showAlectDialog(final Context context, final Contact contact, final Radio_Two_Adapter adapter) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage("确定要删除当前联系人吗？")
                .setIcon(R.mipmap.ic_warning)
                .setTitle("系统提示")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactsManager.deleteData(context,contact);
                        List<Contact> list = new ArrayList<Contact>();
                        list.add(contact);
                        adapter.removeDatas(list);
                        dialog.dismiss();
                    }
                });
            builder.create().show();
    }

}

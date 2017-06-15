package com.example.contactsbook.Adatper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactsbook.R;
import com.example.contactsbook.entry.Calllog;
import com.example.contactsbook.entry.Contact;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.ImageManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class Radio_Two_Adapter extends MyBaseAdapter<Contact> {
    private LayoutInflater inflater;
    private Context context;
    public Radio_Two_Adapter(Context context, List<Contact> list) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }




    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = list.get(position);
        Holder holder = null;
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(R.layout.radio_two_frag_child, null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_radio02);
            holder.textView1 = (TextView) view.findViewById(R.id.tv1_radio02);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }
        if (position <= 0) {
            holder.imageView.setImageResource(R.mipmap.ic_add_contact);
            holder.textView1.setText("增加联系人");
        }else {
            if (contact.getPhotoId() != 0) {
                Bitmap bitmap = ContactsManager.getPhotoByPhotoId(context, contact.getPhotoId());
                Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap);
                holder.imageView.setImageBitmap(bitmap1);
            }else{
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_contact);
                Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap);
                holder.imageView.setImageBitmap(bitmap1);
            }
        }
            holder.textView1.setText(contact.getName());

        return view;
    }
    class Holder{
        ImageView imageView;
        TextView textView1;

    }
}

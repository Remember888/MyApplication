package com.example.contactsbook.Adatper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactsbook.R;
import com.example.contactsbook.entry.Conversation;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.ImageManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class Radio_Four_Adapter extends MyBaseAdapter<Conversation> {
    private LayoutInflater inflater;
    private Context context;
   // private View view;

    public Radio_Four_Adapter(Context context, List<Conversation> list) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
     //   addDatas(list,true);
        Log.d("KKK",list.size() + "");

    }



    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        View view = null;
        Conversation conversation = list.get(position);
        if (convertView == null) {
            view = inflater.inflate(R.layout.radio_one_frag_child, null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_radio01);
            holder.textView1 = (TextView) view.findViewById(R.id.tv1_radio01);
            holder.textView2 = (TextView) view.findViewById(R.id.tv2_radio01);
            holder.textView3 = (TextView) view.findViewById(R.id.tv3_radio01);
            holder.imageView01 = (ImageView) view.findViewById(R.id.iv01_outgoing_radio01);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }


        if (conversation.getPhotoId() != 0) {
            Bitmap bitmap = ContactsManager.getPhotoByPhotoId(context, conversation.getPhotoId());
            Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap);
            Log.d("ttt", bitmap1.toString());
            holder.imageView.setImageBitmap(bitmap1);
        }else{
            Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_contact);
            Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap2);
            holder.imageView.setImageBitmap(bitmap1);
        }
        if (conversation.getName() != null) {
            holder.textView1.setTextColor(Color.BLACK);
            holder.textView1.setText(conversation.getName());
        } else {
            holder.textView1.setTextColor(Color.RED);
            holder.textView1.setText(conversation.getAddress());
        }
        if (conversation.getRead() == 1) {
            holder.imageView01.setVisibility(View.GONE);
        } else if (conversation.getRead() == 2){
            holder.imageView01.setVisibility(View.VISIBLE);
        }
        holder.textView3.setText(ContactsManager.formatDate(conversation.getDate()));
        holder.textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        holder.textView2.setText(conversation.getBody());



        return view;
    }
    class Holder{
        ImageView imageView;
        ImageView imageView01;
        TextView textView1;
        TextView textView2;
        TextView textView3;

    }
}

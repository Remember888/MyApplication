package com.example.contactsbook.Adatper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactsbook.R;
import com.example.contactsbook.entry.Calllog;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.ImageManager;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class Radio_One_Adapter extends MyBaseAdapter<Calllog> {
    private LayoutInflater inflater;
    private Context context;
   // private View view;

    public Radio_One_Adapter(Context context, List<Calllog> list) {
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
        Calllog calllog = list.get(position);
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


        if (calllog.getPhotoId() != 0) {
            Bitmap bitmap = ContactsManager.getPhotoByPhotoId(context, calllog.getPhotoId());
            Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap);
            Log.d("sfs", bitmap1.toString());
            holder.imageView.setImageBitmap(bitmap1);
        }else{
            Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_contact);
            Bitmap bitmap1 = ImageManager.formalBitmap(context, bitmap2);
            holder.imageView.setImageBitmap(bitmap1);
        }
        if (calllog.getName() != null) {
            holder.textView1.setTextColor(Color.BLACK);
            holder.textView1.setText(calllog.getName());
        } else {
            holder.textView1.setTextColor(Color.RED);
            holder.textView1.setText("陌生电话");
        }
        if (calllog.getType() == 1) {
            holder.imageView01.setVisibility(View.GONE);
        } else if (calllog.getType() == 2){
            holder.imageView01.setVisibility(View.VISIBLE);
        }
        holder.textView3.setText(calllog.getDateStr());
        holder.textView2.setText(calllog.getPhone());



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

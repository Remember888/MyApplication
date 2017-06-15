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
import com.example.contactsbook.entry.SMS;
import com.example.contactsbook.manager.ContactsManager;
import com.example.contactsbook.manager.ImageManager;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class SMSAdapter extends MyBaseAdapter<SMS>{
    public static final int LEFT_TYPE=0;
    public static final int RIGHT_TYPE=1;
    private LayoutInflater layoutInflater;
    private Context context;
    public SMSAdapter(Context context) {
        super(context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getItemViewType(int position) {
        int type =  getItem(position).getType();
        return type - 1;
    }

    //通过重写该方法实现返回多个布局数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder=null;
        int type = getItemViewType(position);
        if(view==null){
            if(type == LEFT_TYPE){
                //使用左边布局做数据适配
                view=layoutInflater.inflate(R.layout.chat_receiver_layout,null);
            }else if (type==RIGHT_TYPE){
                //使用右边布局做数据适配
                view=layoutInflater.inflate(R.layout.chat_send_layout,null);
            }
            holder=new ViewHolder();
            holder.textView_Date= (TextView)
                    view.findViewById(
                            R.id.tv_chat);
            holder.imageView_Header= (ImageView)
                    view.findViewById(
                            R.id.iv_chat_avatar);
            holder.textView_Body= (TextView)
                    view.findViewById(
                            R.id.tv_chat_dialog);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        //获得要适配的数据对象
        SMS sms=getItem(position);
        holder.textView_Date.setText(sms.getDateStr());
        holder.textView_Body.setText(sms.getBody());
        //头像的设置
        //如果是收到的短信
        if(type==LEFT_TYPE){
            int photoId=sms.getPhotoId();
            if (photoId != 0) {
                Bitmap header = ContactsManager.
                        getPhotoByPhotoId(context, photoId);
                //格式化头像
                header = ImageManager.formalBitmap(context, header);
                holder.imageView_Header.setImageBitmap(header);
            } else {
                Bitmap header= BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.ic_contact);
                header = ImageManager.formalBitmap(context, header);
                holder.imageView_Header.setImageBitmap(header);
            }
        }else if(type==RIGHT_TYPE){
            //如果是发出去的短信
            //头像使用默认的
            Bitmap header= BitmapFactory.decodeResource(
                    context.getResources(),
                    R.mipmap.ic_contact_selected);
            header = ImageManager.formalBitmap(context, header);
            holder.imageView_Header.setImageBitmap(header);
        }
        return view;
    }

    private class ViewHolder{
        ImageView imageView_Header;
        TextView textView_Date;
        TextView textView_Body;
    }
    }


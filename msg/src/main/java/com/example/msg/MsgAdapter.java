package com.example.msg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/22 0022.
 */

public class MsgAdapter extends ArrayAdapter {
    private Holder holder;
    private int res;
    public MsgAdapter(Context context, int resource, List<Msg> textViewResourceId) {
        super(context, resource, textViewResourceId);
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = (Msg) getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(res, null);
            holder = new Holder();
            holder.layoutLeft = (LinearLayout) view.findViewById(R.id.LL_left);
            holder.layoutRight = (LinearLayout) view.findViewById(R.id.LL_right);
            holder.textLeft = (TextView) view.findViewById(R.id.tv_left);
            holder.textRight = (TextView) view.findViewById(R.id.tv_right);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }
        if (msg.getType() == Msg.RECEIVED_MSG) {
            holder.layoutLeft.setVisibility(View.VISIBLE);
            holder.layoutRight.setVisibility(View.GONE);
            holder.textLeft.setText(msg.getContent());
        } else if (msg.getType() == Msg.SEND_MSG) {
            holder.layoutRight.setVisibility(View.VISIBLE);
            holder.layoutLeft.setVisibility(View.GONE);
            holder.textRight.setText(msg.getContent());
        }
        return view;
    }
    class Holder{
        LinearLayout layoutLeft;
        LinearLayout layoutRight;
        TextView textLeft;
        TextView textRight;
    }
}

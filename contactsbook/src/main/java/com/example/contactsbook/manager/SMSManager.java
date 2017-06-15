package com.example.contactsbook.manager;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.example.contactsbook.constant.Constant;
import com.example.contactsbook.entry.Contact;
import com.example.contactsbook.entry.Conversation;
import com.example.contactsbook.entry.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class SMSManager {
    // 短信会话Uri
    public static final Uri CONVERSATION_URI = Uri
            .parse("content://mms-sms/conversations");
    // 短信Uri 对应的ContentProvider会协调处理短信的收件箱和发件箱
    public static final Uri SMS_URI = Uri.parse("content://sms");
    // 短信发件箱:
    public static final Uri SMS_SEND_URI = Uri.parse("content://sms/sent");
    // 短信收件箱:
    public static final Uri SMS_INBOX_URI = Uri.parse("content://sms/inbox");

    public static List<Conversation> getAllConversations(Context context) {
        List<Conversation> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CONVERSATION_URI,
                new String[]{"thread_id", "address", "body", "read","date"},
                null, null, null
        );
        while (cursor.moveToNext()) {
            int thread_id = cursor.getInt(0);
            String address = cursor.getString(1);
            String body = cursor.getString(2);
            int read = cursor.getInt(3);
            long date = cursor.getLong(4);

            Conversation conversation = new Conversation();
            conversation.set_id(thread_id);
            conversation.setAddress(address);
            conversation.setRead(read);
            conversation.setBody(body);
            conversation.setDate(date);
            conversation.setName(ContactsManager.getNameByNumber(context,address));
            conversation.setPhotoId(ContactsManager.getPhotoIdByNumber(context,address));
            conversation.setDateStr(ContactsManager.formatDate(date));
            list.add(conversation);
        }

        return list;
    }

    public static List<SMS> getAllSMS(Context context,int threadId) {
        List<SMS> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(SMS_URI, new String[]{
                "_id", "body", "type", "date", "address"
        },"thread_id=?",new String[]{String.valueOf(threadId)},"date asc");

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String body = cursor.getString(1);
            int type = cursor.getInt(2);
            long date = cursor.getLong(3);
            String address = cursor.getString(4);

            SMS sms = new SMS();
            sms.set_id(_id);
            sms.setDate(date);
            sms.setBody(body);
            sms.setAddres(address);
            sms.setType(type);
            sms.setPhotoId(ContactsManager.getPhotoIdByNumber(context,address));
            sms.setDateStr(smsDateStr(date));
            list.add(sms);
        }
        cursor.close();
        return list;
    }
    public static String smsDateStr(long date){
        int date1 = ContactsManager.daydiff(date);
        String dateStr = "";
        if (date1 == 0) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            dateStr = format.format(new Date(date1));
        } else if (date1 == 1) {
            SimpleDateFormat format = new SimpleDateFormat("昨天 HH:mm");
            dateStr = format.format(new Date(date1));
        } else if (date1 <= 7) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            HashMap<String, String> map = new HashMap<>();
            map.put("1", "星期天");
            map.put("2", "星期一");
            map.put("3", "星期二");
            map.put("4", "星期三");
            map.put("5", "星期四");
            map.put("6", "星期五");
            map.put("7", "星期六");
            dateStr = map.get(String.valueOf(week));
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = format.format(new Date(date1));
        }
        return dateStr;
    }

    public static void updateConveration(Context context,int threadId){
        ContentValues values = new ContentValues();
        values.put("read",1);
        context.getContentResolver().update(SMS_INBOX_URI, values, "thread_id=?", new String[]{String.valueOf(threadId)});
    }

    public static void deleteConveration(Context context, int threadId) {
        context.getContentResolver().delete(CONVERSATION_URI, "thread_id=?", new String[]{String.valueOf(threadId)});
    }

    /**
     * 解析广播接收器拦截到的短信的内容
     * @param context
     * @param intent 封装了短信内容的意图
     * @return
     */
    public static SMS onReceiver(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] smsMessage = new SmsMessage[pdus.length];
        String format = bundle.getString("format");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage1 = null;
            if (Build.VERSION.SDK_INT < 23) {
                smsMessage1 = SmsMessage.createFromPdu((byte[]) pdus[i]);
            } else {
                smsMessage1 = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            }
            smsMessage[i] = smsMessage1;
        }
        StringBuilder builder = new StringBuilder();
        String address = "";
        long date = 0;
        for (int i = 0; i < smsMessage.length; i++) {
            if (i == 0) {
                address = smsMessage[i].getOriginatingAddress();
                date = smsMessage[i].getTimestampMillis();
            }
            builder.append(smsMessage[i].getDisplayMessageBody());
        }
        SMS sms = new SMS();
        sms.setAddres(address);
        sms.setDate(date);
        sms.setBody(builder.toString());
        sms.setType(1);
        return sms;
    }

    /**
     * 将收到的短信存入到收件箱中
     * @param context
     * @param sms 收到的短信
     * @param threadId 短信所属的会话Id
     */
    public static void saveReceiverSMS(Context context, int threadId, SMS sms) {
        ContentValues values = new ContentValues();
        values.put("body", sms.getBody());
        values.put("threadId", sms.get_id());
        values.put("date", sms.getDate());
        values.put("address", sms.getAddress());
        values.put("type", sms.getType());
        values.put("read",1);
        context.getContentResolver().insert(SMS_INBOX_URI, values);
    }
    //发送消息
    public static void sendSMS(Context context, String message,String address) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> messages = smsManager.divideMessage(message);
        for (int i = 0; i < messages.size(); i++) {
            String body = messages.get(i);

            Intent intent = new Intent();
            intent.putExtra("body", body);
            intent.putExtra("address", address);
            intent.setAction(Constant.SEND_SMS);
            PendingIntent pendIntent = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            smsManager.sendTextMessage(address, null, body, pendIntent,null);
        }
    }
    //收件箱
    public static void saveSendSMS(Context context, String address, String body) {
        ContentValues values = new ContentValues();
        values.put("address", address);
        values.put("body", body);
        values.put("type", 2);
        values.put("date", System.currentTimeMillis());
        context.getContentResolver().insert(SMS_SEND_URI, values);
    }
}

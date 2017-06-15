package com.example.contactsbook.manager;

import android.app.IntentService;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import com.example.contactsbook.constant.Constant;
import com.example.contactsbook.entry.Calllog;
import com.example.contactsbook.entry.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class ContactsManager implements Constant {


    private static int week;

    public static List<Contact> getAllContacts(Context context) {
        List<Contact> list = new ArrayList<Contact>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"_id", "photo_id"}, null, null, null);

        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int photo_id = cursor.getInt(cursor.getColumnIndex("photo_id"));
            contact.setId(_id);
            contact.setPhotoId(photo_id);

            Uri data_Uri = ContactsContract.Data.CONTENT_URI;
            Cursor data_cursor = context.getContentResolver().query(data_Uri, new String[]{ContactsContract.Data.MIMETYPE, ContactsContract.Data.DATA1},
                    ContactsContract.Data.RAW_CONTACT_ID + "=?",
                    new String[]{String.valueOf(_id)}, null);
            while (data_cursor.moveToNext()) {
                String data1 = data_cursor.getString(data_cursor.getColumnIndex(ContactsContract.Data.DATA1));
                String mimetyle = data_cursor.getString(data_cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                if (mimetyle.equals(Constant.MIMETYLE_ADDRESS)) {
                    contact.setAddress(data1);
                } else if (mimetyle.equals(Constant.MIMETYLE_EMAIL)) {
                    contact.setEmail(data1);
                } else if (mimetyle.equals(Constant.MIMETYLE_NAME)) {
                    contact.setName(data1);
                } else if (mimetyle.equals(Constant.MIMETYLE_PHONO)) {
                    contact.setPhone(data1);
                }
            }

            data_cursor.close();
            list.add(contact);
        }
        cursor.close();
        return list;
    }

    public static Bitmap getPhotoByPhotoId(Context contenxt, int photoId) {
        Bitmap photo = null;
        Cursor cursor = contenxt.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data.DATA15},
                ContactsContract.Data._ID + "=?",
                new String[]{String.valueOf(photoId)},
                null);
        if (cursor.moveToNext()) {
            byte[] data = cursor.getBlob(0);
            photo = BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return  photo;
    }

    public static void deleteData(Context context, Contact contact) {
        context.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    public static List<Calllog> getPhotoByCalllog(Context context) {
        List<Calllog> list = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    new String[]{CallLog.Calls._ID,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.TYPE,
                            CallLog.Calls.DATE},
                    null, null, CallLog.Calls.DATE + " desc");
            while (cursor.moveToNext()) {
                Calllog calllog = new Calllog();
                int _id = cursor.getInt(0);
                String phone = cursor.getString(1);
                int type = cursor.getInt(2);
                long date = cursor.getLong(3);
                int photoId = getPhotoIdByNumber(context, phone);
                String name = getNameByNumber(context, phone);



                calllog.set_id(_id);
                calllog.setType(type);
                calllog.setPhone(phone);
                calllog.setDate(date);
                calllog.setPhotoId(photoId);
                calllog.setName(name);
                calllog.setDateStr(formatDate(date));
                list.add(calllog);
            }
            cursor.close();

        } catch (SecurityException e) {
            e.printStackTrace();
        }
            return list;
    }


    /**
     * 计算通话时间和当前系统时间的天数差
     * @return 天数差
     */
    public static int daydiff(long stamp){
        int diff=0;
        //获得当前系统时间的日历对象
        Calendar calendar1=
                Calendar.getInstance();
        //创建一个表示通话时间的日历对象
        Calendar calendar2=Calendar.
                getInstance();
        //把通话时间戳设置给calendar2
        calendar2.setTimeInMillis(stamp);

        diff=calendar1.get(Calendar.DAY_OF_YEAR)-
                calendar2.get(Calendar.DAY_OF_YEAR);

        return  diff;
    }
    public static String formatDate(
            long stamp){
        String dateStr=null;
        //获得天数差
        int diff=daydiff(stamp);
        if(diff==0){
            //说明通话时间为当天
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("HH:mm:ss");
            dateStr=dateFormat.format(new Date(stamp));
        }else if(diff==1){
            //说明通话时间是昨天
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("昨天 HH:mm:ss");
            dateStr=dateFormat.format(new Date(stamp));
        }else if(diff<=7){
            //通话时间是一周以内的
            Calendar calendar=
                    Calendar.getInstance();
            calendar.setTimeInMillis(stamp);
            int weekDay=calendar.
                    get(Calendar.DAY_OF_WEEK);
            switch (weekDay){
                case Calendar.MONDAY:
                    dateStr="星期一";
                    break;
                case Calendar.TUESDAY:
                    dateStr="星期二";
                    break;
                case Calendar.WEDNESDAY:
                    dateStr="星期三";
                    break;
                case Calendar.THURSDAY:
                    dateStr="星期四";
                    break;
                case Calendar.FRIDAY:
                    dateStr="星期五";
                    break;
                case Calendar.SATURDAY:
                    dateStr="星期六";
                    break;
                case Calendar.SUNDAY:
                    dateStr="星期日";
                    break;
                default:
                    break;
            }

        }else{
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("yyyy-MM-dd");
            dateStr=dateFormat.format(new Date(stamp));
        }

        return dateStr;
    }



    public static int getPhotoIdByNumber(Context context, String number) {
        int photoId = 0;
        Cursor cursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number)),
                new String[]{ContactsContract.PhoneLookup.PHOTO_ID}, null, null, null);
        if (cursor.moveToNext()) {
            photoId = cursor.getInt(0);
        }
        return photoId;
    }

    public static String getNameByNumber(Context context, String number) {
        String name = null;
        Cursor cursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number)),
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null,null);
        if (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        return name;
    }
}

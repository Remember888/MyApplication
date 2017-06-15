package com.example.contactsbook.entry;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class SMS {
    private int _id;
    private String body;
    private String address;
    private long date;
    private String dateStr;
    private int type;
    private int photoId;

    public SMS() {

    }

    public SMS(int _id, String body, String address, long date, String dateStr, int type, int photoId) {
        this._id = _id;
        this.body = body;
        this.address = address;
        this.date = date;
        this.dateStr = dateStr;
        this.type = type;
        this.photoId = photoId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddres(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "_id=" + _id +
                ", body='" + body + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", type=" + type +
                ", photoId=" + photoId +
                '}';
    }
}

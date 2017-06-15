package com.example.contactsbook.entry;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class Conversation {
    private int _id;
    private String address;
    private String body;
    private int read;
    private String name;
    private int photoId;
    private long date;
    private String dateStr;

    public Conversation() {
    }

    public Conversation(int _id, String name, String address, String body, long date, String dateStr, int photoId, int read) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.body = body;
        this.date = date;
        this.dateStr = dateStr;
        this.photoId = photoId;
        this.read = read;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", photoId=" + photoId +
                ", read=" + read +
                '}';
    }
}

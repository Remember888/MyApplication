package com.example.contactsbook.entry;

import android.graphics.Bitmap;
import android.support.annotation.VisibleForTesting;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class Contact {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private int photoId;
    private Bitmap bitmap;
    public Contact() {
    }

    public Contact(int photoId, int id, String name, String phone, String address, String email) {
        this.photoId = photoId;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getPhotoId() {
        return photoId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneId=" + photoId +
                +
                '}';
    }
}

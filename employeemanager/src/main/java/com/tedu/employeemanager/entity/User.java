package com.tedu.employeemanager.entity;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class User {
    int id;
    String localname;
    String password;
    String realname;
    String email;

    public User() {
    }

    public User(int id, String localname, String password, String realname, String email) {
        this.id = id;
        this.localname = localname;
        this.password = password;
        this.realname = realname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

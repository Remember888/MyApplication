package com.example.volley;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class Person {
    private int id;
    private String name;
    private int agge;

    public Person() {
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

    public int getAgge() {
        return agge;
    }

    public void setAgge(int agge) {
        this.agge = agge;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", agge=" + agge +
                '}';
    }

}

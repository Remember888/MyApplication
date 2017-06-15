package com.example.msg;

/**
 * Created by Administrator on 2017/4/22 0022.
 */

public class Msg {
    public static final int RECEIVED_MSG = 0;
    public static final int SEND_MSG = 1;
    private int type;
    private String content;
    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }


    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}

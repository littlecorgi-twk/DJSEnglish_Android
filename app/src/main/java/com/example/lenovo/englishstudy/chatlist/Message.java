package com.example.lenovo.englishstudy.chatlist;

public class Message {

    /**
     * sender : 手机用户13227769909
     * text : 测试消息
     * time : 2019-05-27 21:31:56
     */

    private String sender;
    private String text;
    private String time;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

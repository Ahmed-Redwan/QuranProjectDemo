package com.example.quranprojectdemo.chat;

public class Chat {

    String sender;
    String reciver;
    String massege;
    boolean seen;

    public Chat() {
    }

    public Chat(String sender, String reciver, String massege, boolean seen) {
        this.sender = sender;
        this.reciver = reciver;
        this.massege = massege;
        this.seen = seen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }


}

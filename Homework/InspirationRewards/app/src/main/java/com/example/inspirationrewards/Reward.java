package com.example.inspirationrewards;

import java.io.Serializable;

public class Reward implements Serializable {

    //Attributes
    private int amount = 0;
    private String username = "";
    private String senderName = "";
    private String comment = "";
    private String date = "";

    public Reward(
        int a,
        String u,
        String s,
        String c,
        String d
    ){
        amount = a;
        username = u;
        senderName = s;
        comment = c;
        date = d;
    }

    //--GET----

    public int getAmount() {
        return amount;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getComment() {
        return comment;
    }

    public String getDate(){
        return date;
    }

    public String getUsername(){
        return username;
    }
    //--SET----

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

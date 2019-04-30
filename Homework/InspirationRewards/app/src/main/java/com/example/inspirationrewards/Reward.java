package com.example.inspirationrewards;

import java.io.Serializable;

public class Reward implements Serializable {

    //Attributes
    private int amount = 0;
    private String sender = "";
    private String comment = "";
    private String date = "";

    public Reward(){
        amount = 0;
        sender = "";
        comment = "";
        date = "";
    }

    public Reward(
        int a,
        String s,
        String c,
        String d
    ){
        amount = a;
        sender = s;
        comment = c;
        date = d;
    }

    //--GET----

    public int getAmount() {
        return amount;
    }

    public String getSender() {
        return sender;
    }

    public String getComment() {
        return comment;
    }

    public String getDate(){
        return date;
    }
    //--SET----

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

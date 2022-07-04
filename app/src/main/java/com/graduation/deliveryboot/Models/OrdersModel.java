package com.graduation.deliveryboot.Models;

public class OrdersModel {
    private String From;
    private String To;
    private String date;
    private String time;
    private String ReceiveCode;
    private String name;
    private String Receiver ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getReceiveCode() {
        return ReceiveCode;
    }

    public void setReceiveCode(String recieveCode) {
        ReceiveCode = recieveCode;
    }


    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        this.From = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

package com.graduation.deliveryboot.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ErrorModel {

    @SerializedName("code")
    @Expose
    private long code;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     */
    public ErrorModel() {
    }

    /**
     * @param code
     * @param msg
     */
    public ErrorModel(long code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

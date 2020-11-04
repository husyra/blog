package com.ronglian.hh.domain;

import java.io.Serializable;
import java.util.Date;

public class Friend implements Serializable {

    private String owner;       //关注人

    private String friend;      //被关注人

    private String comment;     //备注

    private String phone;       //手机号

    private String address;     //地址

    private Date addTime;       //关注时间

    private Date updateTime;    //更新时间

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "owner='" + owner + '\'' +
                ", friend='" + friend + '\'' +
                ", comment='" + comment + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addTime='" + addTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

}

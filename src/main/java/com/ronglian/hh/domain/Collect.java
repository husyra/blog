package com.ronglian.hh.domain;

import java.io.Serializable;
import java.util.Date;

public class Collect implements Serializable {

    private String owner;       //收藏者

    private String object;      //对象ID

    private String type;        //对象类型：BLOG微博，COMM评论

    private Date addTime;       //收藏时间

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "owner='" + owner + '\'' +
                ", object='" + object + '\'' +
                ", type='" + type + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }

}

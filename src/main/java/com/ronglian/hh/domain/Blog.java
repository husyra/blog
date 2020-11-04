package com.ronglian.hh.domain;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {

    private String id;          //001 +15位序列号

    private String owner;       //发布人

    private String title;       //标题

    private String content;     //内容

    private String image;       //图像

    private String type;        //类型：0微博，1笔记

    private Date pubTime;       //发布时间

    private Date updateTime;    //更新时间

    private String status;      //状态：1公开，2仅自己可见，3部分好友可见，4删除

    private int hot;         //热度：如被评论次数


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", pubTime=" + pubTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", hot='" + hot + '\'' +
                '}';
    }

}

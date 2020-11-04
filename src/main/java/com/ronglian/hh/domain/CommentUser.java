package com.ronglian.hh.domain;

import java.util.Date;
import java.util.List;

public class CommentUser {

    private String id;          //1002开头，后面32位为不可重复随机数

    private String object;      //对象ID

    private String parent;      //父评论id，默认为'0'

    private String owner;       //发布者

    private String content;     //内容

    private Date pubTime;       //发布时间

    private int hot;            //热度

    private String userName;

    private String userPhoto;

    private List<CommentUser> childComm;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<CommentUser> getChildComm() {
        return childComm;
    }

    public void setChildComm(List<CommentUser> childComm) {
        this.childComm = childComm;
    }

    @Override
    public String toString() {
        return "CommentUser{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", parent='" + parent + '\'' +
                ", owner='" + owner + '\'' +
                ", content='" + content + '\'' +
                ", pubTime=" + pubTime +
                ", hot=" + hot +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", childComm='" + childComm + '\'' +
                '}';
    }
}

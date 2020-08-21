package com.w.school_herper_front.bean;

import java.io.Serializable;

/**
 * @CONTENT: FriendBean
 * @DEVELOPER: Zhangxixian
 * @DATE: 19/5/29
 */
public class Friend implements Serializable {
    private String headPhoto;
    private String name;
    private String message;
    private String time;

    public Friend(String headPhoto, String name, String message, String time) {
        this.headPhoto = headPhoto;
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

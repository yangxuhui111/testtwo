package com.w.school_herper_front.HomePage.fragment.board;

import com.w.school_herper_front.R;

import java.io.Serializable;

/*
* 功能：board
* 开发人：尚一飞
* 日期：2018.12.12
* 简介：用于生成布告内容 与BoardFragment  board交互使用
* */
public class board implements Serializable {
    private int myhead = R.drawable.myhead;
    private int userId;
    private int rewardId;
    private String name;
    private String sex;
    private String title;
    private int image;
    private String content;
    private String rewardTime;
    private String endTime;
    private String money;
    private String state;

    public board(){}
    public board(int myhead, String name, String title, String content, String endTime, String money) {
        this.myhead = myhead;
        this.name = name;
        this.title = title;
        this.content = content;
        this.endTime = endTime;
        this.money = money;
    }

    public board(int myhead, String name, String title, int image, String content, String endTime, String money) {
        this.myhead = myhead;
        this.name = name;
        this.title = title;
        this.image = image;
        this.content = content;
        this.endTime = endTime;
        this.money = money;
    }

    public int getMyhead() {
        return myhead;
    }

    public void setMyhead(int myhead) {
        this.myhead = myhead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(String rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.w.school_herper_front;

/*
 * 功能：用户信息类的创建和获取数值
 * 开发人：赵璐
 * 开发时间：2018.11.27
 *
 * 描述：注册页面，相关xml：activity_main.xml
 */

import android.hardware.usb.UsbRequest;

public class User {
    private String phone;
    private String password;
    private String name;
    private String school;
    private String stuNumber;
    private String stuName;
    private String stuWriter;
    private String sex;
    private int userId;
    private String schoolId;
    private String image;
    private int value;
    private double money;
    private int took;
    private int publish;
    private String realname;
    private String identification;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }


    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuWriter() {
        return stuWriter;
    }

    public void setStuWriter(String stuWriter) {
        this.stuWriter = stuWriter;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public User(){

    }
    public User(String phone,double money){
        this.money=money;
        this.phone=phone;
    }
    public User(String password){
        this.password=password;
    }
    public User(String phone, String password){
        this.phone = phone;
        this.password = password;
    }
    public User(String identification,String name,String realname,String phone,String sex,String stuWriter){
        this.identification=identification;
        this.name=name;
        this.realname=realname;
        this.phone=phone;
        this.sex=sex;
        this.stuWriter=stuWriter;
    }
    public User(String phone, String password, String name, String school, String stuNumber) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.school = school;
        this.stuNumber = stuNumber;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


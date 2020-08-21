package com.w.school_herper_front.bean;

public class ReviseUser {
    private int userId;
    private String headPhoto;
    private String username;
    private String names;
    private String autograph;
    private String sex;
    private String phone;
    private String authentication;

    public ReviseUser(){}

    public ReviseUser(String headPhoto,String username,String names,String autograph,String sex,String phone,String authentication){
        setHeadPhoto(headPhoto);
        setUsername(username);
        setNames(names);
        setAutograph(autograph);
        setSex(sex);
        setPhone(phone);
        setAuthentication(authentication);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }
    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}

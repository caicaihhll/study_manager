package com.java.managersystem.entity;

import java.io.Serializable;


//本地数据库存储的user数据
public class LocalUserInfoBean implements Serializable {
    private int userId;
    private String account;
    private String password;
    private String nickName;
    private int avatar;
    private int role;

    public LocalUserInfoBean() {
    }

    public LocalUserInfoBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

package com.example.will.protocol.user;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * 用户ID
     */
    private long userId;
    /**
     * 用户账户
     */
    private String account;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别 1: 男生 2: 女生 3:未知
     */
    private int gender;
    /**
     * 出生日期
     */
    private String birth;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 用户头像 url
     */
    private String avatarUrl;
    /**
     * 用户密码
     */
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

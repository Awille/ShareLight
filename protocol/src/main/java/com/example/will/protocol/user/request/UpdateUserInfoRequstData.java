package com.example.will.protocol.user.request;

import com.example.will.protocol.user.User;

import java.io.Serializable;

public class UpdateUserInfoRequstData implements Serializable {
    /**
     * 用户实例 nickName account gender Birth Signature
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

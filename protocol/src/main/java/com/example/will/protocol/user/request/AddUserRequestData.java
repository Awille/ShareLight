package com.example.will.protocol.user.request;

import com.example.will.protocol.user.User;

import java.io.Serializable;

public class AddUserRequestData implements Serializable {
    /**
     * 用户信息
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

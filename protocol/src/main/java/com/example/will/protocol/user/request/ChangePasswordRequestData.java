package com.example.will.protocol.user.request;

import com.example.will.protocol.user.User;

import java.io.Serializable;

public class ChangePasswordRequestData implements Serializable {
    /**
     * 用户实例 account password
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

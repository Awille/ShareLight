package com.example.will.protocol.user.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class UserLoginRequest extends CommonRequest implements Serializable {
    private UserLoginRequestData data;

    public UserLoginRequestData getData() {
        return data;
    }

    public void setData(UserLoginRequestData data) {
        this.data = data;
    }
}

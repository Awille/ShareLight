package com.example.will.protocol.user.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class ChangePasswordRequest extends CommonRequest implements Serializable {

    ChangePasswordRequestData data;

    public ChangePasswordRequestData getData() {
        return data;
    }

    public void setData(ChangePasswordRequestData data) {
        this.data = data;
    }
}

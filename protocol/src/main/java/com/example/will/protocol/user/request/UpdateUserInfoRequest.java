package com.example.will.protocol.user.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class UpdateUserInfoRequest extends CommonRequest implements Serializable {

    private UpdateUserInfoRequstData data;

    public UpdateUserInfoRequstData getData() {
        return data;
    }

    public void setData(UpdateUserInfoRequstData data) {
        this.data = data;
    }
}

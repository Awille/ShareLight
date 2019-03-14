package com.example.will.protocol.user.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class AddUserRequest extends CommonRequest implements Serializable {
    /**
     * 增加用户数据
     */
    AddUserRequestData data;

    public AddUserRequestData getData() {
        return data;
    }

    public void setData(AddUserRequestData data) {
        this.data = data;
    }
}

package com.example.will.protocol.user.response;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.user.User;

import java.io.Serializable;

public class QueryUserResponse extends CommonResponse implements Serializable {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}

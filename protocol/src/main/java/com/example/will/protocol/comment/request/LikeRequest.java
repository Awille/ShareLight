package com.example.will.protocol.comment.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class LikeRequest extends CommonRequest implements Serializable {
    private LikeRequestData data;

    public LikeRequestData getData() {
        return data;
    }

    public void setData(LikeRequestData data) {
        this.data = data;
    }
}

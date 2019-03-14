package com.example.will.protocol.comment.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class AddCommentRequest extends CommonRequest implements Serializable {
    private AddCommentRequestData data;

    public AddCommentRequestData getData() {
        return data;
    }

    public void setData(AddCommentRequestData data) {
        this.data = data;
    }
}

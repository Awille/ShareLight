package com.example.will.protocol.comment.reponse;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.comment.Comment;

import java.io.Serializable;

public class QueryCommentResponse extends CommonResponse implements Serializable {
    private Comment data;

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
        this.data = data;
    }
}

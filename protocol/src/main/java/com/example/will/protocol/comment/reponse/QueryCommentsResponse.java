package com.example.will.protocol.comment.reponse;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.comment.Comment;

import java.io.Serializable;
import java.util.List;

public class QueryCommentsResponse extends CommonResponse implements Serializable {
    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}

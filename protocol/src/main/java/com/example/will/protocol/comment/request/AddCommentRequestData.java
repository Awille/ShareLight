package com.example.will.protocol.comment.request;

import com.example.will.protocol.comment.Comment;

import java.io.Serializable;

public class AddCommentRequestData implements Serializable {
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

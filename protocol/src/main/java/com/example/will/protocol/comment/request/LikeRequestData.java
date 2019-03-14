package com.example.will.protocol.comment.request;

import java.io.Serializable;

public class LikeRequestData implements Serializable {
    private long commentId;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
}

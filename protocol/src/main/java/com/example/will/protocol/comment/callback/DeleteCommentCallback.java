package com.example.will.protocol.comment.callback;

import com.example.will.protocol.comment.reponse.DeleteCommentResponse;

public interface DeleteCommentCallback {
    void onDeleteCommentSuccess(DeleteCommentResponse response);
    void onDeleteCommentFail(String errCode, String errMsg);
}

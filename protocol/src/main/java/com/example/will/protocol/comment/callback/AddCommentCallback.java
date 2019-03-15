package com.example.will.protocol.comment.callback;

import com.example.will.protocol.comment.reponse.AddCommentResponse;

public interface AddCommentCallback {
    void onAddCommentSuccess(AddCommentResponse response);
    void onAddCommentFail(String errCode, String errMsg);
}

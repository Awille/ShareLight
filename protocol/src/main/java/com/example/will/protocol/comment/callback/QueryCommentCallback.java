package com.example.will.protocol.comment.callback;

import com.example.will.protocol.comment.reponse.QueryCommentResponse;

public interface QueryCommentCallback {
    void onQueryCommentSuccess(QueryCommentResponse response);
    void onQueryCommentFail(String errCode, String errMsg);
}

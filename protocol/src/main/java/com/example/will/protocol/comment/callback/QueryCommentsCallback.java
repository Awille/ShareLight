package com.example.will.protocol.comment.callback;

import com.example.will.protocol.comment.reponse.QueryCommentsResponse;

public interface QueryCommentsCallback {
    void onQueryCommentsSuccess(QueryCommentsResponse response);
    void onQueryCommentFail(String errCode, String errMsG);
}

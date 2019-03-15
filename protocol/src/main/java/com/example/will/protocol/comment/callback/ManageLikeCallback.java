package com.example.will.protocol.comment.callback;

import com.example.will.protocol.comment.reponse.LikeResponse;

public interface ManageLikeCallback {
    void onManageLikeSuccess(LikeResponse response);
    void onManageLikeFail(String errCode, String errMsg);
}

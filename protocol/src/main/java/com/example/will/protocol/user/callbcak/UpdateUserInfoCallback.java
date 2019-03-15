package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.UpdateUserInfoResponse;

public interface UpdateUserInfoCallback {
    void onUpdateUserInfoSuccess(UpdateUserInfoResponse response);
    void onUpdateUserInfoFail(String errCode, String errMsg);
}

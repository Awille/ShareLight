package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.ChangePasswordResponse;

public interface ChangePasswordCallback {
    void onChangePasswordSuccess(ChangePasswordResponse response);
    void onChangePasswordFail(String errCode, String errMsg);
}

package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.UserLoginResponse;

public interface UserLoginCallback {
    void onUserLoginSuccess(UserLoginResponse response);
    void onUserLoginFail(String errCode, String errMsg);
}

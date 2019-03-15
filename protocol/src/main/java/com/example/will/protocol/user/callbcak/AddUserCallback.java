package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.AddUserResponse;

public interface AddUserCallback {
    void onAddUserSuccess(AddUserResponse response);
    void onAddUserFail(String errCode, String errMsg);
}

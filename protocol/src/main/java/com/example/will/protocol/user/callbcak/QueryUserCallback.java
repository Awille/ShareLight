package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.QueryUserResponse;

public interface QueryUserCallback {
    void onQueryUserSuccess(QueryUserResponse response);
    void onQueryUserFail(String errCode, String errMsg);
}

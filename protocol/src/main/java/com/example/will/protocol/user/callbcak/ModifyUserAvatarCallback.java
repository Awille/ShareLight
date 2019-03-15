package com.example.will.protocol.user.callbcak;

import com.example.will.protocol.user.response.ModifyUserAvatarResponse;

public interface ModifyUserAvatarCallback {
    void onModifyUserAvatarSuccess(ModifyUserAvatarResponse response);
    void onModifyUserAvatarFail(String errCode, String errMsg);
}

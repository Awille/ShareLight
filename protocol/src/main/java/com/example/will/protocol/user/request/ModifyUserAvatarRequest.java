package com.example.will.protocol.user.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class ModifyUserAvatarRequest extends CommonRequest implements Serializable {
    private ModifyUserAvatarRequestData data;

    public ModifyUserAvatarRequestData getData() {
        return data;
    }

    public void setData(ModifyUserAvatarRequestData data) {
        this.data = data;
    }
}

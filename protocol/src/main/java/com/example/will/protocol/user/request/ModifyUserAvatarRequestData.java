package com.example.will.protocol.user.request;

import com.example.will.protocol.UploadFile;

import java.io.Serializable;

public class ModifyUserAvatarRequestData implements Serializable {
    private UploadFile uploadFile;

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}

package com.example.will.protocol.songlist.request;

import com.example.will.protocol.UploadFile;

import java.io.Serializable;

public class UploadSongListFileRequestData implements Serializable {
    private UploadFile uploadFile;

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}

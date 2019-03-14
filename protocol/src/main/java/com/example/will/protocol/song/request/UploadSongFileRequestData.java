package com.example.will.protocol.song.request;

import com.example.will.protocol.UploadFile;

import java.io.Serializable;

public class UploadSongFileRequestData implements Serializable {
    private UploadFile uploadFile;

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}

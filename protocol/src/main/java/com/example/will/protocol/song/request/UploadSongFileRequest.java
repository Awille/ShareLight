package com.example.will.protocol.song.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class UploadSongFileRequest extends CommonRequest implements Serializable {
    private UploadSongFileRequestData data;

    public UploadSongFileRequestData getData() {
        return data;
    }

    public void setData(UploadSongFileRequestData data) {
        this.data = data;
    }
}

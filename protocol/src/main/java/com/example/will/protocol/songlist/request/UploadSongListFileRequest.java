package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;
import com.example.will.protocol.song.request.UploadSongFileRequestData;

import java.io.Serializable;

public class UploadSongListFileRequest extends CommonRequest implements Serializable {
    private UploadSongFileRequestData data;

    public UploadSongFileRequestData getData() {
        return data;
    }

    public void setData(UploadSongFileRequestData data) {
        this.data = data;
    }
}

package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;
import com.example.will.protocol.song.request.UploadSongFileRequestData;

import java.io.Serializable;

public class UploadSongListFileRequest extends CommonRequest implements Serializable {
    private UploadSongListFileRequestData data;

    public UploadSongListFileRequestData getData() {
        return data;
    }

    public void setData(UploadSongListFileRequestData data) {
        this.data = data;
    }
}

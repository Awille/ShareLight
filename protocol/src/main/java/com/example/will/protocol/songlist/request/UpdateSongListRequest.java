package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;
import com.example.will.protocol.song.request.UploadSongFileRequestData;

import java.io.Serializable;

public class UpdateSongListRequest extends CommonRequest implements Serializable {
    private UpdateSongListRequestData data;

    public UpdateSongListRequestData getData() {
        return data;
    }

    public void setData(UpdateSongListRequestData data) {
        this.data = data;
    }
}

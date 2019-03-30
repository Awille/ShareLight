package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;
import com.example.will.protocol.song.request.AddSongRequestData;

import java.io.Serializable;

public class AddSongToSongListRequest extends CommonRequest implements Serializable {
    private AddSongRequestData data;

    public AddSongRequestData getData() {
        return data;
    }

    public void setData(AddSongRequestData data) {
        this.data = data;
    }
}

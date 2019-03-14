package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class AddSongListRequest extends CommonRequest implements Serializable {
    private AddSongListRequestData data;

    public AddSongListRequestData getData() {
        return data;
    }

    public void setData(AddSongListRequestData data) {
        this.data = data;
    }
}

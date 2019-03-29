package com.example.will.protocol.song.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class AddSongRequest extends CommonRequest implements Serializable {
    private AddSongRequestData data;

    public AddSongRequestData getData() {
        return data;
    }

    public void setData(AddSongRequestData data) {
        this.data = data;
    }
}

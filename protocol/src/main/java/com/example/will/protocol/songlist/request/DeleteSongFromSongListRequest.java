package com.example.will.protocol.songlist.request;

import com.example.will.protocol.CommonRequest;

import java.io.Serializable;

public class DeleteSongFromSongListRequest extends CommonRequest implements Serializable {
    private DeleteSongFromSongListRequestData data;

    public DeleteSongFromSongListRequestData getData() {
        return data;
    }

    public void setData(DeleteSongFromSongListRequestData data) {
        this.data = data;
    }
}

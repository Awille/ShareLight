package com.example.will.protocol.songlist.response;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.songlist.SongList;

import java.io.Serializable;

public class AddSongListResponse extends CommonResponse implements Serializable {
    private SongList data;

    public SongList getData() {
        return data;
    }

    public void setData(SongList data) {
        this.data = data;
    }
}

package com.example.will.protocol.song.response;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.song.Song;

import java.io.Serializable;

public class QuerySongResponse extends CommonResponse implements Serializable {
    private Song data;

    public Song getData() {
        return data;
    }

    public void setData(Song data) {
        this.data = data;
    }
}

package com.example.will.protocol.song.response;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.song.Song;

import java.io.Serializable;
import java.util.List;

public class QuerySongsResponse extends CommonResponse implements Serializable {
    private List<Song> data;

    public List<Song> getData() {
        return data;
    }

    public void setData(List<Song> data) {
        this.data = data;
    }
}

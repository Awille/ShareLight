package com.example.will.protocol.songlist.response;

import com.example.will.protocol.CommonResponse;
import com.example.will.protocol.songlist.SongList;

import java.io.Serializable;
import java.util.List;

public class QuerySongListsResponse extends CommonResponse implements Serializable {
    private List<SongList> data;

    public List<SongList> getData() {
        return data;
    }

    public void setData(List<SongList> data) {
        this.data = data;
    }
}

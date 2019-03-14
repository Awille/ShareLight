package com.example.will.protocol.songlist.request;

import java.io.Serializable;

public class UpdateSongListRequestData implements Serializable {
    private long songListId;
    private String name;

    public long getSongListId() {
        return songListId;
    }

    public void setSongListId(long songListId) {
        this.songListId = songListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

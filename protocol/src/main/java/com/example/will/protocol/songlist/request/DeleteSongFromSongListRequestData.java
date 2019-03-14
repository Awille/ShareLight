package com.example.will.protocol.songlist.request;

import java.io.Serializable;

public class DeleteSongFromSongListRequestData implements Serializable {
    private long songId;
    private long songListId;

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public long getSongListId() {
        return songListId;
    }

    public void setSongListId(long songListId) {
        this.songListId = songListId;
    }
}

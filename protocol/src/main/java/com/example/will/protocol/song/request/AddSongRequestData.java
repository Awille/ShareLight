package com.example.will.protocol.song.request;

import com.example.will.protocol.song.Song;

import java.io.Serializable;

public class AddSongRequestData implements Serializable {
    private Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}

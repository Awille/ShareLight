package com.example.will.protocol.songlist.request;

import java.io.Serializable;

public class AddSongListRequestData implements Serializable {
    /**
     * 歌单名字
     */
    private String name;
    /**
     * 用户Id
     */
    private long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.AddSongToSongListResponse;

public interface AddSongToSongListCallback {
    void onAddSongToSongListSuccess(AddSongToSongListResponse response);
    void onAddSongToSongListFail(String errCode, String errMsg);
}

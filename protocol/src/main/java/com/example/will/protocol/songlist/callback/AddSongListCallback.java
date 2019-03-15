package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.AddSongListResponse;

public interface AddSongListCallback {
    void onAddSongListSuccess(AddSongListResponse response);
    void onAddSongListFail(String errCode, String errMsg);
}

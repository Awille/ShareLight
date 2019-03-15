package com.example.will.protocol.song.callback;

import com.example.will.protocol.song.response.AddSongResponse;

public interface AddSongCallback {
    void onAddSongSuccess(AddSongResponse response);
    void onAddSongFail(String errCode, String errMsg);
}

package com.example.will.protocol.song.callback;

import com.example.will.protocol.song.response.GetRandomSongResponse;

public interface GetRandomSongCallback {
    void onGetRandomSongSuccess(GetRandomSongResponse response);
    void onGetRandomSongFail(String errCode, String errMsg);
}

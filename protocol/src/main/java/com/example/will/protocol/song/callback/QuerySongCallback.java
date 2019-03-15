package com.example.will.protocol.song.callback;

import com.example.will.protocol.song.response.QuerySongResponse;

public interface QuerySongCallback {
    void onQuerySongSuccess(QuerySongResponse response);
    void onQuerySongFail(String errCode, String errMsg);
}

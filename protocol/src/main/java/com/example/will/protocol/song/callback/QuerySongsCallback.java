package com.example.will.protocol.song.callback;

import com.example.will.protocol.song.response.QuerySongsResponse;

public interface QuerySongsCallback {
    void onQuerySongsSuccess(QuerySongsResponse response);
    void onQuerySongFail(String errCode, String errMsg);
}

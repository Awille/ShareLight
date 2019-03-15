package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.QuerySongListResponse;

public interface QuerySongListCallback {
    void onQuerySongListSuccess(QuerySongListResponse response);
    void onQuerySongListFail(String errCode, String errMsg);
}

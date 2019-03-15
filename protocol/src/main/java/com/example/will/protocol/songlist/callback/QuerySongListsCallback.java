package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.QuerySongListResponse;

public interface QuerySongListsCallback {
    void onQuerySongListsCallback(QuerySongListResponse response);
    void onQuerySongListsFail(String errCode, String errMsg);
}

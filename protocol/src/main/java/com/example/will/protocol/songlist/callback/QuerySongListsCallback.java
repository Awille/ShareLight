package com.example.will.protocol.songlist.callback;


import com.example.will.protocol.songlist.response.QuerySongListsResponse;

public interface QuerySongListsCallback {
    void onQuerySongListsSuccess(QuerySongListsResponse response);
    void onQuerySongListsFail(String errCode, String errMsg);
}

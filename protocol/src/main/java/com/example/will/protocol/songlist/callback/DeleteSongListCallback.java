package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.DeleteSongListResponse;

public interface DeleteSongListCallback {
    void onDeleteSongListSuccess(DeleteSongListResponse response);
    void onDeleteSongListFail(String errCode, String errMsg);
}

package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.DeleteSongFromSongListResponse;

public interface DeleteSongFromSongListCallback {
    void onDelteSongFromSongListSuccess(DeleteSongFromSongListResponse response);
    void onDeleteSongFromSongListFail(String errCode, String errMsg);
}

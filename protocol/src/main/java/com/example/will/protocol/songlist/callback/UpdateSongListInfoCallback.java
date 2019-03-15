package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.UpdateSongListResponse;

public interface UpdateSongListInfoCallback {
    void onUpdateSongListInfoSucess(UpdateSongListResponse response);
    void onUpdateSongListInfoFail(String errCode, String errMsg);
}

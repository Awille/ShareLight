package com.example.will.protocol.songlist.callback;

import com.example.will.protocol.songlist.response.UploadSongListFileResponse;

public interface UploadSongListFileCallback {
    void onUploadSongListFileSuccess(UploadSongListFileResponse response);
    void onUploadSongListFileFail(String errCode, String errMsg);
}

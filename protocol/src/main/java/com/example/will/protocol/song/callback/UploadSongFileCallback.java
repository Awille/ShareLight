package com.example.will.protocol.song.callback;

import com.example.will.protocol.song.response.UploadSongFileReponse;

public interface UploadSongFileCallback {
    void onUploadSongFileSuccess(UploadSongFileReponse reponse);
    void onUploadSongFileFail(String errCode, String errMsg);
}

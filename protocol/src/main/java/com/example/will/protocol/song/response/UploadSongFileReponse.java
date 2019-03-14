package com.example.will.protocol.song.response;

import com.example.will.protocol.CommonResponse;

import java.io.Serializable;

public class UploadSongFileReponse extends CommonResponse implements Serializable {
    /**
     * 此处返回上传后资源存储的Url
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

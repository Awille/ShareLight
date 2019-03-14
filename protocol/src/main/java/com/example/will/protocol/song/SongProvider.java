package com.example.will.protocol.song;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.song.request.AddSongRequest;
import com.example.will.protocol.song.request.UploadSongFileRequest;
import com.example.will.protocol.song.response.AddSongResponse;
import com.example.will.protocol.song.response.QuerySongResponse;
import com.example.will.protocol.song.response.QuerySongsResponse;
import com.example.will.protocol.song.response.UploadSongFileReponse;

/**
 * 歌曲基础能力协议层
 */
public interface SongProvider extends BasicProvider {

    /**
     * 根据songId查询歌曲
     * @param songId
     * @param callback
     */
    void querySong(String songId, HttpCallback<QuerySongResponse> callback);

    /**
     * 根据 作者author 或者 歌名songName 获取歌曲 list
     * @param key author OR songName
     * @param callback
     */
    void querySongs(String key, HttpCallback<QuerySongsResponse> callback);

    /**
     * 上传歌曲文件
     * @param request
     * @param callback
     */
    void uploadSongFile(UploadSongFileRequest request, HttpCallback<UploadSongFileReponse> callback);

    /**
     * 添加歌曲
     * @param request
     * @param callback
     */
    void addSong(AddSongRequest request, HttpCallback<AddSongResponse> callback);
}

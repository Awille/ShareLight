package com.example.will.protocol.song;

import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.song.callback.AddSongCallback;
import com.example.will.protocol.song.callback.GetRandomSongCallback;
import com.example.will.protocol.song.callback.QuerySongCallback;
import com.example.will.protocol.song.callback.QuerySongsCallback;
import com.example.will.protocol.song.callback.UploadSongFileCallback;
import com.example.will.protocol.song.request.AddSongRequest;
import com.example.will.protocol.song.request.UploadSongFileRequest;

/**
 * 歌曲基础能力协议层
 */
public interface SongProvider extends BasicProvider {

    /**
     * 根据songId查询歌曲
     * @param songId
     * @param callback
     */
    void querySong(long songId, QuerySongCallback callback);

    /**
     * 根据 作者author 或者 歌名songName 获取歌曲 list
     * @param songName
     * @param author
     * @param callback
     */
    void querySongs(String songName, long author,  QuerySongsCallback callback);

    /**
     * 上传歌曲文件
     * @param request
     * @param callback
     */
    void uploadSongFile(UploadSongFileRequest request, UploadSongFileCallback callback);

    /**
     * 添加歌曲
     * @param request
     * @param callback
     */
    void addSong(AddSongRequest request, AddSongCallback callback);

    void getRandomSong(String random, GetRandomSongCallback callback);
}

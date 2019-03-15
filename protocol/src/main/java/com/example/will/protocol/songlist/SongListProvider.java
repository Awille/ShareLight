package com.example.will.protocol.songlist;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.songlist.callback.AddSongListCallback;
import com.example.will.protocol.songlist.callback.AddSongToSongListCallback;
import com.example.will.protocol.songlist.callback.DeleteSongFromSongListCallback;
import com.example.will.protocol.songlist.callback.DeleteSongListCallback;
import com.example.will.protocol.songlist.callback.QuerySongListCallback;
import com.example.will.protocol.songlist.callback.QuerySongListsCallback;
import com.example.will.protocol.songlist.callback.UpdateSongListInfoCallback;
import com.example.will.protocol.songlist.callback.UploadSongListFileCallback;
import com.example.will.protocol.songlist.request.AddSongListRequest;
import com.example.will.protocol.songlist.request.AddSongToSongListRequest;
import com.example.will.protocol.songlist.request.DeleteSongFromSongListRequest;
import com.example.will.protocol.songlist.request.UpdateSongListRequest;
import com.example.will.protocol.songlist.request.UploadSongListFileRequest;

/**
 * 歌单基础能力
 */
public interface SongListProvider extends BasicProvider {
    /**
     * 根据songListId查找歌单
     * @param songListId
     * @param callback
     */
    void querySongList(long songListId, QuerySongListCallback callback);

    /**
     * 根据用户ID 获取全部歌单
     * @param userId
     * @param callback
     */
    void querySongLists(long userId, QuerySongListsCallback callback);

    /**
     * 添加歌单
     * @param request
     * @param callback
     */
    void addSongList(AddSongListRequest request, AddSongListCallback callback);

    /**
     * 上传歌单相关文件
     * @param request
     * @param callback
     */
    void uploadSongListFile(UploadSongListFileRequest request, UploadSongListFileCallback callback);

    /**
     * 更新歌单基本信息
     * @param request
     * @param callback
     */
    void updateSongListInfo(UpdateSongListRequest request, UpdateSongListInfoCallback callback);

    /**
     * 添加歌曲到歌单
     * @param request
     * @param callback
     */
    void addSongToSongList(AddSongToSongListRequest request, AddSongToSongListCallback callback);

    /**
     * 删除歌曲
     * @param request
     * @param callback
     */
    void deleSongFromSongList(DeleteSongFromSongListRequest request, DeleteSongFromSongListCallback callback);

    /**
     * 删除歌单
     * @param songListId
     * @param callback
     */
    void deleteSongList(long songListId, DeleteSongListCallback callback);
}

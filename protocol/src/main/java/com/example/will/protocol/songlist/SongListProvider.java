package com.example.will.protocol.songlist;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.songlist.request.AddSongListRequest;
import com.example.will.protocol.songlist.request.AddSongToSongListRequest;
import com.example.will.protocol.songlist.request.DeleteSongFromSongListRequest;
import com.example.will.protocol.songlist.request.UpdateSongListRequest;
import com.example.will.protocol.songlist.request.UploadSongListFileRequest;
import com.example.will.protocol.songlist.response.AddSongListResponse;
import com.example.will.protocol.songlist.response.AddSongToSongListResponse;
import com.example.will.protocol.songlist.response.DeleteSongFromSongListResponse;
import com.example.will.protocol.songlist.response.DeleteSongListResponse;
import com.example.will.protocol.songlist.response.QuerySongListResponse;
import com.example.will.protocol.songlist.response.QuerySongListsResponse;
import com.example.will.protocol.songlist.response.UpdateSongListResponse;
import com.example.will.protocol.songlist.response.UploadSongListFileResponse;

/**
 * 歌单基础能力
 */
public interface SongListProvider extends BasicProvider {
    /**
     * 根据songListId查找歌单
     * @param songListId
     * @param callback
     */
    void querySongList(long songListId, HttpCallback<QuerySongListResponse> callback);

    /**
     * 根据用户ID 获取全部歌单
     * @param userId
     * @param callback
     */
    void querySongLists(long userId, HttpCallback<QuerySongListsResponse> callback);

    /**
     * 添加歌单
     * @param request
     * @param callback
     */
    void addSongList(AddSongListRequest request, HttpCallback<AddSongListResponse> callback);

    /**
     * 上传歌单相关文件
     * @param request
     * @param callback
     */
    void uploadSongListFile(UploadSongListFileRequest request, HttpCallback<UploadSongListFileResponse> callback);

    /**
     * 更新歌单基本信息
     * @param request
     * @param callback
     */
    void updateSongListInfo(UpdateSongListRequest request, HttpCallback<UpdateSongListResponse> callback);

    /**
     * 添加歌曲到歌单
     * @param request
     * @param callback
     */
    void addSongToSongList(AddSongToSongListRequest request, HttpCallback<AddSongToSongListResponse> callback);

    /**
     * 删除歌曲
     * @param request
     * @param callback
     */
    void deleSongFromSongList(DeleteSongFromSongListRequest request, HttpCallback<DeleteSongFromSongListResponse> callback);

    /**
     * 删除歌单
     * @param songListId
     * @param callback
     */
    void deleteSongList(long songListId, HttpCallback<DeleteSongListResponse> callback);
}

package com.example.will.songlistdomain;


import com.example.will.network.retrofit.HttpCall;
import com.example.will.network.retrofit.RetrofitMrg;
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

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SongListApiService {
    @GET(RetrofitMrg.songListUrl)
    HttpCall<QuerySongListResponse> querySongList(@Query("songListId") long songListId);

    @GET(RetrofitMrg.songListUrl)
    HttpCall<QuerySongListsResponse> querySongLists(@Query("userId") long userId);

    @POST(RetrofitMrg.songListUrl)
    HttpCall<AddSongListResponse> addSongList(@Body AddSongListRequest request);

    @PUT(RetrofitMrg.songListUrl)
    HttpCall<UploadSongListFileResponse> uploadSongListFile(@Body UploadSongListFileRequest request);

    @PUT(RetrofitMrg.songListUrl)
    HttpCall<UpdateSongListResponse> updateSongListInfo(@Body UpdateSongListRequest request);

    @PUT(RetrofitMrg.songListUrl)
    HttpCall<AddSongToSongListResponse> addSongToSongList(@Body AddSongToSongListRequest request);

    @PUT(RetrofitMrg.songListUrl)
    HttpCall<DeleteSongFromSongListResponse> deleteSongFromSongList(@Body DeleteSongFromSongListRequest request);

    @DELETE(RetrofitMrg.songListUrl)
    HttpCall<DeleteSongListResponse> deleteSongList(@Query("songListId") long songListId);
}

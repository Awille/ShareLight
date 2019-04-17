package com.example.will.songdomain;

import com.example.will.network.retrofit.HttpCall;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.song.request.AddSongRequest;
import com.example.will.protocol.song.request.UploadSongFileRequest;
import com.example.will.protocol.song.response.AddSongResponse;
import com.example.will.protocol.song.response.GetRandomSongResponse;
import com.example.will.protocol.song.response.QuerySongResponse;
import com.example.will.protocol.song.response.QuerySongsResponse;
import com.example.will.protocol.song.response.UploadSongFileReponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SongApiService {
    @GET(RetrofitMrg.songUrl)
    HttpCall<QuerySongResponse> querySong(@Query("songId") long songId);

    @GET(RetrofitMrg.songUrl)
    HttpCall<QuerySongsResponse> querySongs(@Query("songName") String songName, @Query("author") long author);

    @POST(RetrofitMrg.songUrl)
    HttpCall<UploadSongFileReponse> uploadSongFile(@Body UploadSongFileRequest request);

    @POST(RetrofitMrg.songUrl)
    HttpCall<AddSongResponse> addSong(@Body AddSongRequest request);

    @GET(RetrofitMrg.songUrl)
    HttpCall<GetRandomSongResponse> getRandomSong(@Query("random") String random);
}

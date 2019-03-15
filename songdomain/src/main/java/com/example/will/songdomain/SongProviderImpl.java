package com.example.will.songdomain;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.network.retrofit.RequestManager;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.SongProvider;
import com.example.will.protocol.song.callback.AddSongCallback;
import com.example.will.protocol.song.callback.QuerySongCallback;
import com.example.will.protocol.song.callback.QuerySongsCallback;
import com.example.will.protocol.song.callback.UploadSongFileCallback;
import com.example.will.protocol.song.request.AddSongRequest;
import com.example.will.protocol.song.request.UploadSongFileRequest;
import com.example.will.protocol.song.response.AddSongResponse;
import com.example.will.protocol.song.response.QuerySongResponse;
import com.example.will.protocol.song.response.QuerySongsResponse;
import com.example.will.protocol.song.response.UploadSongFileReponse;

public class SongProviderImpl implements SongProvider {

    private static SongApiService songApiService = RetrofitMrg.getINSTANCE().getRetrofit().create(SongApiService.class);

    private static String SUCCESS = CommonConstant.NetWork.SUCCESS;


    @Override
    public void querySong(long songId, final QuerySongCallback callback) {
        RequestManager.call(songApiService.querySong(songId), new HttpCallback<QuerySongResponse>() {
            @Override
            public void onSuccess(QuerySongResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onQuerySongSuccess(respObj);
                } else {
                    callback.onQuerySongFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQuerySongFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQuerySongFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void querySongs(String songName, long author, final QuerySongsCallback callback) {
        RequestManager.call(songApiService.querySongs(songName, author), new HttpCallback<QuerySongsResponse>() {
            @Override
            public void onSuccess(QuerySongsResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onQuerySongsSuccess(respObj);
                } else {
                    callback.onQuerySongFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQuerySongFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQuerySongFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void uploadSongFile(final UploadSongFileRequest request, final UploadSongFileCallback callback) {
        RequestManager.call(songApiService.uploadSongFile(request), new HttpCallback<UploadSongFileReponse>() {
            @Override
            public void onSuccess(UploadSongFileReponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onUploadSongFileSuccess(respObj);
                } else {
                    callback.onUploadSongFileFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onUploadSongFileFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onUploadSongFileFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void addSong(final AddSongRequest request, final AddSongCallback callback) {
        RequestManager.call(songApiService.addSong(request), new HttpCallback<AddSongResponse>() {
            @Override
            public void onSuccess(AddSongResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onAddSongSuccess(respObj);
                } else {
                    callback.onAddSongFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onAddSongFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onAddSongFail(errCode, errMsg);
            }
        });
    }
}

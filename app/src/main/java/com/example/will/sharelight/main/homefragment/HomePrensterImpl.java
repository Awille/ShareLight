package com.example.will.sharelight.main.homefragment;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.song.callback.QuerySongsCallback;
import com.example.will.protocol.song.response.QuerySongsResponse;
import com.example.will.protocol.songlist.callback.QuerySongListsCallback;
import com.example.will.protocol.songlist.response.QuerySongListsResponse;

public class HomePrensterImpl implements HomeContract.HomePresenter {

    private static final String TAG = "HomePrensterImpl";

    private HomeContract.HomeView homeView;

    public HomePrensterImpl(HomeContract.HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void querySongLists(long userId) {
        Log.e(TAG, "userId: " + userId);
        MusicProvider.getINSTANCE().getSongListProvider().querySongLists(userId, new QuerySongListsCallback() {
            @Override
            public void onQuerySongListsSuccess(QuerySongListsResponse response) {
                homeView.onQuerySongListsSuccess(response.getData());
            }

            @Override
            public void onQuerySongListsFail(String errCode, String errMsg) {
                homeView.onQuerySongListsFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void queryUploadSongs(long userId) {
        Log.e(TAG, "userId: " + userId);
        MusicProvider.getINSTANCE().getSongProvider().querySongs(null, userId, new QuerySongsCallback() {
            @Override
            public void onQuerySongsSuccess(QuerySongsResponse response) {
                homeView.onQueryUploadSongsSuccess(response.getData());
            }

            @Override
            public void onQuerySongFail(String errCode, String errMsg) {
                homeView.onQueryUploadSongFail(errCode, errMsg);
            }
        });
    }
}

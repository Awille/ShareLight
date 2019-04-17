package com.example.will.sharelight.main.square;

import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.song.callback.GetRandomSongCallback;
import com.example.will.protocol.song.response.GetRandomSongResponse;

public class RecommendPresenterImpl implements RecommendContract.RecommendPresenter {

    private RecommendContract.RecommendView recommendView;

    public RecommendPresenterImpl(RecommendContract.RecommendView view) {
        recommendView = view;
    }

    @Override
    public void getRecommendSong(String random) {
        MusicProvider.getINSTANCE().getSongProvider().getRandomSong(random, new GetRandomSongCallback() {
            @Override
            public void onGetRandomSongSuccess(GetRandomSongResponse response) {
                recommendView.onGetRecommendSongSuccess(response.getData());
            }

            @Override
            public void onGetRandomSongFail(String errCode, String errMsg) {
                recommendView.onGetRecommendSongFail(errCode, errMsg);
            }
        });
    }
}

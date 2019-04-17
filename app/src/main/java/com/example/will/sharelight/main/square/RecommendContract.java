package com.example.will.sharelight.main.square;

import com.example.will.protocol.song.Song;

public class RecommendContract {
    public interface RecommendPresenter {
        void getRecommendSong(String random);
    }

    public interface RecommendView {
        void onGetRecommendSongSuccess(Song song);
        void onGetRecommendSongFail(String errCode, String errMsg);
    }
}

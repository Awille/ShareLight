package com.example.will.sharelight.main.homefragment;

import com.example.will.protocol.song.Song;
import com.example.will.protocol.songlist.SongList;

import java.util.List;

public class HomeContract {
    public interface HomePresenter {
        void querySongLists(long userId);
        void queryUploadSongs(long userId);
    }

    public interface HomeView {
        void onQuerySongListsSuccess(List<SongList> songLists);
        void onQuerySongListsFail(String errCode, String errMsg);

        void onQueryUploadSongsSuccess(List<Song> songs);
        void onQueryUploadSongFail(String errCode, String errMsg);
    }
}

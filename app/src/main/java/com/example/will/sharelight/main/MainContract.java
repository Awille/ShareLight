package com.example.will.sharelight.main;

import com.example.will.protocol.UploadFile;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.user.User;

public class MainContract {
     public interface MainPresenter {
         void updateUserInfo(User user);
         void changeUserAvatar(UploadFile uploadFile);
         void addSong(Song song);
         void changeSongAvatar(UploadFile uploadFile);
         void changeSongResource(UploadFile uploadFile);
         void changeSongListAvatar(UploadFile uploadFile);
         void deleteSongList(long songListId);
         void changeSongListName(String name, long songListId);
     }

     public interface MainView {
         void onUpdateUserInfoSuccess(User user);
         void onUpdateUserInfoFail(String errCode, String errMsg);

         void onChangeUserAvatarSuccess();
         void onChangeUserAvatarFail(String errCode, String errMsg);

         void onAddSongSuccess(Song song);
         void onAddSongFail(String errCode, String errMsg);

         void onChangeSongAvatarSuccess();
         void onChangeSongAvatarFail(String errCode, String errMsg);

         void onChangeSongResourceSuccess();
         void onChangeSongResourceFail(String errCode, String errMsg);

         void onChangeSongListAvatarSuccess();
         void onChangeSongListAvatarFail(String errCode, String errMsg);

         void onDeleteSongListSuccess();
         void onDeleteSongListFail(String errCode, String errMsg);

         void onChangeSongListNameSuccess();
         void onChangeSongListNameFail(String errCode, String errMsg);
     }
}

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
     }
}

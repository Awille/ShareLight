package com.example.will.sharelight.main;

import com.example.will.protocol.UploadFile;
import com.example.will.protocol.user.User;

public class MainContract {
     public interface MainPresenter {
         void updateUserInfo(User user);
         void changeUserAvatar(UploadFile uploadFile);
     }

     public interface MainView {
         void onUpdateUserInfoSuccess(User user);
         void onUpdateUserInfoFail(String errCode, String errMsg);

         void onChangeUserAvatarSuccess();
         void onChangeUserAvatarFail(String errCode, String errMsg);
     }
}

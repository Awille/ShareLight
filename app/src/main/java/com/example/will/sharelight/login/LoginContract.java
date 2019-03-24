package com.example.will.sharelight.login;

import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.user.User;

public class LoginContract {
    public interface LoginPresenter {
        void signUp(String account, String nickName, String password);
        void signIn(String account, String password);

        void pullUserInfo(String account);
    }

    public interface LoginView {
        void onSignUpSuccess(User user);
        void onSignUpFail(String errCode, String errMsg);

        void onSignInSuccess(User user);
        void onSignInFail(String errCode, String errMsg);

        void onPullUserInfoSuccess(User user);
        void onPullUserInfoFail(String errCode, String errMsg);

    }
}

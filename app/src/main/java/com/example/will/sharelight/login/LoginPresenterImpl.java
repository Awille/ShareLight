package com.example.will.sharelight.login;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.user.User;
import com.example.will.protocol.user.callbcak.AddUserCallback;
import com.example.will.protocol.user.callbcak.UserLoginCallback;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.AddUserRequestData;
import com.example.will.protocol.user.request.UserLoginRequest;
import com.example.will.protocol.user.request.UserLoginRequestData;
import com.example.will.protocol.user.response.AddUserResponse;
import com.example.will.protocol.user.response.UserLoginResponse;

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";

    LoginContract.LoginView loginView;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void signUp(String account, String nickName, String password) {
        User user = new User();
        user.setAccount(account);
        user.setNickName(nickName);
        user.setPassword(password);
        AddUserRequest addUserRequest = new AddUserRequest();
        AddUserRequestData data = new AddUserRequestData();
        data.setUser(user);
        addUserRequest.setData(data);
        MusicProvider.getINSTANCE().getUserProvider().addUser(addUserRequest, new AddUserCallback() {
            @Override
            public void onAddUserSuccess(AddUserResponse response) {
                loginView.onSignUpSuccess(response.getData());
            }

            @Override
            public void onAddUserFail(String errCode, String errMsg) {
                loginView.onSignUpFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void signIn(String account, String password) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        final UserLoginRequest request = new UserLoginRequest();
        request.setService("104");
        UserLoginRequestData data = new UserLoginRequestData();
        data.setAccount(account);
        data.setPassword(password);
        request.setData(data);
        Log.e(TAG, JSON.toJSONString(request));
        MusicProvider.getINSTANCE().getUserProvider().userLogin(request, new UserLoginCallback() {
            @Override
            public void onUserLoginSuccess(UserLoginResponse response) {
                loginView.onSignInSuccess(response.getData());
            }

            @Override
            public void onUserLoginFail(String errCode, String errMsg) {
                loginView.onSignInFail(errCode, errMsg);
            }
        });
    }
}

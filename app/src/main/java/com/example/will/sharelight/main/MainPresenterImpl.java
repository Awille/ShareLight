package com.example.will.sharelight.main;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.user.User;
import com.example.will.protocol.user.callbcak.UpdateUserInfoCallback;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequstData;
import com.example.will.protocol.user.response.UpdateUserInfoResponse;

public class MainPresenterImpl implements MainContract.MainPresenter {
    private static final String TAG = "MainPresenterImpl";

    private MainContract.MainView mainView;

    public MainPresenterImpl(MainContract.MainView mainView) {
        this.mainView = mainView;
    }


    @Override
    public void updateUserInfo(User user) {
        UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
        updateUserInfoRequest.setService("101");
        UpdateUserInfoRequstData data = new UpdateUserInfoRequstData();
        data.setUser(user);
        updateUserInfoRequest.setData(data);
        MusicProvider.getINSTANCE().getUserProvider().updateUserInfo(updateUserInfoRequest, new UpdateUserInfoCallback() {
            @Override
            public void onUpdateUserInfoSuccess(UpdateUserInfoResponse response) {
                mainView.onUpdateUserInfoSuccess(response.getData());
            }

            @Override
            public void onUpdateUserInfoFail(String errCode, String errMsg) {
                mainView.onUpdateUserInfoFail(errCode, errMsg);
            }
        });
    }
}

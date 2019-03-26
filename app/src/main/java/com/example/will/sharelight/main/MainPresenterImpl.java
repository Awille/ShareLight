package com.example.will.sharelight.main;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.UploadFile;
import com.example.will.protocol.user.User;
import com.example.will.protocol.user.callbcak.ModifyUserAvatarCallback;
import com.example.will.protocol.user.callbcak.UpdateUserInfoCallback;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequestData;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequstData;
import com.example.will.protocol.user.response.ModifyUserAvatarResponse;
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

    @Override
    public void changeUserAvatar(UploadFile uploadFile) {
        ModifyUserAvatarRequest request = new ModifyUserAvatarRequest();
        request.setService("103");
        ModifyUserAvatarRequestData data = new ModifyUserAvatarRequestData();
        data.setUploadFile(uploadFile);
        request.setData(data);
        MusicProvider.getINSTANCE().getUserProvider().modifyUserAvatar(request, new ModifyUserAvatarCallback() {
            @Override
            public void onModifyUserAvatarSuccess(ModifyUserAvatarResponse response) {
                mainView.onChangeUserAvatarSuccess();
            }

            @Override
            public void onModifyUserAvatarFail(String errCode, String errMsg) {
                mainView.onChangeUserAvatarFail(errCode, errMsg);
            }
        });

    }
}

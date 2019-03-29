package com.example.will.sharelight.main;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.will.datacontext.MusicDataContext;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.UploadFile;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.song.callback.AddSongCallback;
import com.example.will.protocol.song.callback.UploadSongFileCallback;
import com.example.will.protocol.song.request.AddSongRequest;
import com.example.will.protocol.song.request.AddSongRequestData;
import com.example.will.protocol.song.request.UploadSongFileRequest;
import com.example.will.protocol.song.request.UploadSongFileRequestData;
import com.example.will.protocol.song.response.AddSongResponse;
import com.example.will.protocol.song.response.UploadSongFileReponse;
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

    @Override
    public void addSong(Song song) {
        AddSongRequest addSongRequest = new AddSongRequest();
        addSongRequest.setService("203");
        AddSongRequestData data = new AddSongRequestData();
        data.setSong(song);
        addSongRequest.setData(data);
        Log.e(TAG, "AddSongRequest" + JSON.toJSONString(addSongRequest));
        MusicProvider.getINSTANCE().getSongProvider().addSong(addSongRequest, new AddSongCallback() {
            @Override
            public void onAddSongSuccess(AddSongResponse response) {
                mainView.onAddSongSuccess(response.getData());
            }

            @Override
            public void onAddSongFail(String errCode, String errMsg) {
                mainView.onAddSongFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void changeSongAvatar(UploadFile uploadFile) {
        UploadSongFileRequest uploadSongFileRequest = new UploadSongFileRequest();
        uploadSongFileRequest.setService("204");
        UploadSongFileRequestData data = new UploadSongFileRequestData();
        data.setUploadFile(uploadFile);
        uploadSongFileRequest.setData(data);
        MusicProvider.getINSTANCE().getSongProvider().uploadSongFile(uploadSongFileRequest, new UploadSongFileCallback() {
            @Override
            public void onUploadSongFileSuccess(UploadSongFileReponse reponse) {
                Log.e(TAG, "上传结果:" + JSON.toJSONString(reponse));
                mainView.onChangeSongAvatarSuccess();
            }

            @Override
            public void onUploadSongFileFail(String errCode, String errMsg) {
                Log.e(TAG, "上传结果:" + errCode + " " + errMsg);
                mainView.onChangeSongAvatarFail(errCode, errMsg);
            }
        });
    }
}

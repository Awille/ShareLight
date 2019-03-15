package com.example.will.userdomain;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.network.retrofit.RequestManager;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.user.UserProvider;
import com.example.will.protocol.user.callbcak.AddUserCallback;
import com.example.will.protocol.user.callbcak.ChangePasswordCallback;
import com.example.will.protocol.user.callbcak.ModifyUserAvatarCallback;
import com.example.will.protocol.user.callbcak.QueryUserCallback;
import com.example.will.protocol.user.callbcak.UpdateUserInfoCallback;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.ChangePasswordRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.response.AddUserResponse;
import com.example.will.protocol.user.response.ChangePasswordResponse;
import com.example.will.protocol.user.response.ModifyUserAvatarResponse;
import com.example.will.protocol.user.response.QueryUserResponse;
import com.example.will.protocol.user.response.UpdateUserInfoResponse;

public class UserProviderImpl implements UserProvider {

    public static UserApi userApiService = RetrofitMrg.getINSTANCE().getRetrofit().create(UserApi.class);
    public static String SUCCESS = CommonConstant.NetWork.SUCCESS;

    @Override
    public void addUser(AddUserRequest request, final AddUserCallback callback) {
        RequestManager.call(userApiService.addUser(request), new HttpCallback<AddUserResponse>() {
            @Override
            public void onSuccess(AddUserResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onAddUserSuccess(respObj);
                } else {
                    callback.onAddUserFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onAddUserFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onAddUserFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void queryUser(String account, final QueryUserCallback callback) {
        RequestManager.call(userApiService.queryUser(account), new HttpCallback<QueryUserResponse>() {
            @Override
            public void onSuccess(QueryUserResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onQueryUserSuccess(respObj);
                } else {
                    callback.onQueryUserFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQueryUserFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQueryUserFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void changePassword(ChangePasswordRequest request, final ChangePasswordCallback callback) {
        RequestManager.call(userApiService.changePassword(request), new HttpCallback<ChangePasswordResponse>() {
            @Override
            public void onSuccess(ChangePasswordResponse respObj) {
                callback.onChangePasswordSuccess(respObj);
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onChangePasswordFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onChangePasswordFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void updateUserInfo(final UpdateUserInfoRequest request, final UpdateUserInfoCallback callback) {
        RequestManager.call(userApiService.updateUserInfo(request), new HttpCallback<UpdateUserInfoResponse>() {
            @Override
            public void onSuccess(UpdateUserInfoResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onUpdateUserInfoSuccess(respObj);
                } else {
                    callback.onUpdateUserInfoFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onUpdateUserInfoFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onUpdateUserInfoFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void modifyUserAvatar(ModifyUserAvatarRequest request, final ModifyUserAvatarCallback callback) {
        RequestManager.call(userApiService.modifyUserAvatar(request), new HttpCallback<ModifyUserAvatarResponse>() {
            @Override
            public void onSuccess(ModifyUserAvatarResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onModifyUserAvatarSuccess(respObj);
                } else {
                    callback.onModifyUserAvatarFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onModifyUserAvatarFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onModifyUserAvatarFail(errCode, errMsg);
            }
        });
    }
}

package com.example.will.protocol.user;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.ChangePasswordRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.response.AddUserResponse;
import com.example.will.protocol.user.response.ChangePasswordResponse;
import com.example.will.protocol.user.response.ModifyUserAvatarResponse;
import com.example.will.protocol.user.response.QueryUserResponse;
import com.example.will.protocol.user.response.UpdateUserInfoResponse;

/**
 * 用户基础能力
 */
public interface UserProvider extends BasicProvider {
    /**
     * 添加用户
     * @param request
     * @param callback
     */
    void addUser(AddUserRequest request, HttpCallback<AddUserResponse> callback);
    /**
     * 查询用户
     * @param account
     * @param callback
     */
    void queryUser(String account, HttpCallback<QueryUserResponse> callback);
    /**
     * 更改密码
     * @param request
     * @param callback
     */
    void changePassword(ChangePasswordRequest request, HttpCallback<ChangePasswordResponse> callback);
    /**
     * 更新用户信息
     * @param request
     * @param callback
     */
    void updateUserInfo(UpdateUserInfoRequest request, HttpCallback<UpdateUserInfoResponse> callback);
    /**
     * 更改用户图片
     * @param request
     * @param callback
     */
    void uploadUserAvatar(ModifyUserAvatarRequest request, HttpCallback<ModifyUserAvatarResponse> callback);
}

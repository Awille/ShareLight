package com.example.will.protocol.user;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.user.callbcak.AddUserCallback;
import com.example.will.protocol.user.callbcak.ChangePasswordCallback;
import com.example.will.protocol.user.callbcak.ModifyUserAvatarCallback;
import com.example.will.protocol.user.callbcak.QueryUserCallback;
import com.example.will.protocol.user.callbcak.UpdateUserInfoCallback;
import com.example.will.protocol.user.callbcak.UserLoginCallback;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.ChangePasswordRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.request.UserLoginRequest;

/**
 * 用户基础能力
 */
public interface UserProvider extends BasicProvider {
    /**
     * 添加用户
     * @param request
     * @param callback
     */
    void addUser(AddUserRequest request, AddUserCallback callback);

    /**
     * 查询用户
     * @param account
     * @param callback
     */
    void queryUser(String account, QueryUserCallback callback);

    /**
     * 更改密码
     * @param request
     * @param callback
     */
    void changePassword(ChangePasswordRequest request, ChangePasswordCallback callback);

    /**
     * 更新用户信息
     * @param request
     * @param callback
     */
    void updateUserInfo(UpdateUserInfoRequest request, UpdateUserInfoCallback callback);

    /**
     * 更改用户图片
     * @param request
     * @param callback
     */
    void modifyUserAvatar(ModifyUserAvatarRequest request, ModifyUserAvatarCallback callback);

    /**
     * 用户登录
     * @param request
     * @param callback
     */
    void userLogin(UserLoginRequest request, UserLoginCallback callback);
}

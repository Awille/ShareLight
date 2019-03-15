package com.example.will.userdomain;

import com.example.will.network.retrofit.HttpCall;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.ChangePasswordRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.response.AddUserResponse;
import com.example.will.protocol.user.response.ChangePasswordResponse;
import com.example.will.protocol.user.response.ModifyUserAvatarResponse;
import com.example.will.protocol.user.response.QueryUserResponse;
import com.example.will.protocol.user.response.UpdateUserInfoResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {
    @POST("restful//user.jsp")
    HttpCall<AddUserResponse> addUser(@Body AddUserRequest request);

    @GET("restful//user.jsp")
    HttpCall<QueryUserResponse> queryUser(@Query("account") String account);

    @PUT("restful//user.jsp")
    HttpCall<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

    @PUT("restful//user.jsp")
    HttpCall<UpdateUserInfoResponse> updateUserInfo(@Body UpdateUserInfoRequest request);

    @PUT("restful//user.jsp")
    HttpCall<ModifyUserAvatarResponse> modifyUserAvatar(@Body ModifyUserAvatarRequest request);
}

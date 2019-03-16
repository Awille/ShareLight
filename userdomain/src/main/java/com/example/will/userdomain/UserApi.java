package com.example.will.userdomain;

import com.example.will.network.retrofit.HttpCall;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.user.request.AddUserRequest;
import com.example.will.protocol.user.request.ChangePasswordRequest;
import com.example.will.protocol.user.request.ModifyUserAvatarRequest;
import com.example.will.protocol.user.request.UpdateUserInfoRequest;
import com.example.will.protocol.user.request.UserLoginRequest;
import com.example.will.protocol.user.response.AddUserResponse;
import com.example.will.protocol.user.response.ChangePasswordResponse;
import com.example.will.protocol.user.response.ModifyUserAvatarResponse;
import com.example.will.protocol.user.response.QueryUserResponse;
import com.example.will.protocol.user.response.UpdateUserInfoResponse;
import com.example.will.protocol.user.response.UserLoginResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {
    @POST(RetrofitMrg.userUrl)
    HttpCall<AddUserResponse> addUser(@Body AddUserRequest request);

    @GET(RetrofitMrg.userUrl)
    HttpCall<QueryUserResponse> queryUser(@Query("account") String account);

    @PUT(RetrofitMrg.userUrl)
    HttpCall<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

    @PUT(RetrofitMrg.userUrl)
    HttpCall<UpdateUserInfoResponse> updateUserInfo(@Body UpdateUserInfoRequest request);

    @PUT(RetrofitMrg.userUrl)
    HttpCall<ModifyUserAvatarResponse> modifyUserAvatar(@Body ModifyUserAvatarRequest request);

    @PUT(RetrofitMrg.userUrl)
    HttpCall<UserLoginResponse> userLogin(@Body UserLoginRequest request);
}

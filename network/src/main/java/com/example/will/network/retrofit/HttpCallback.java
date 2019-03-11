package com.example.will.network.retrofit;

public interface HttpCallback<T> {
    void onSuccess(T respObj);

    void onError(String errCode, String errMsg);

    void onSystemError(String errCode, String errMsg);
}

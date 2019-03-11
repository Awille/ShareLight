package com.example.will.network.retrofit;

import java.io.IOException;

import retrofit2.Response;

public interface HttpCall<T> {
    void cancel();

    void send(HttpCallback<T> callback);

    Response<T> sendSync() throws IOException;

    HttpCall<T> clone();
}

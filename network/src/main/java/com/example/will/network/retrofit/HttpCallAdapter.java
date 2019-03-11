package com.example.will.network.retrofit;

import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class HttpCallAdapter<T> implements CallAdapter<T, HttpCall<T>> {
    private Type responseType;
    private Executor callbackExecutor;

    public HttpCallAdapter(Type responseType, Executor callbackExecutor) {
        this.responseType = responseType;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public HttpCall<T> adapt(Call<T> call) {
        return new HttpCallbackCall<>(call, callbackExecutor);
    }
}

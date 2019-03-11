package com.example.will.network.retrofit;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpCallbackCall<T> implements HttpCall<T> {
    private Call<T> delegate;
    private Executor callbackExecutor;


    public HttpCallbackCall(Call<T> delegate, Executor callbackExecutor) {
        this.delegate = delegate;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public void cancel() {
        delegate.cancel();
    }

    @Override
    public void send(final HttpCallback<T> callback) {
        delegate.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(String.valueOf(response.code()),
                                    response.message());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onSystemError("SYSTEM_ERROR", t.getMessage());
            }
        });
    }

    @Override
    public Response<T> sendSync() throws IOException {
        return delegate.execute();
    }

    @Override
    public HttpCall<T> clone() {
        return new HttpCallbackCall<>(delegate, callbackExecutor);
    }
}

package com.example.will.network.retrofit;

public class RequestManager {
    public static <T> void call(HttpCall<T> request, HttpCallback<T> callback) {
        if (request != null) {
            request.send(callback);
        }
    }
}

package com.example.will.network.retrofit;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class HttpCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != HttpCall.class) {
            return null;
        }
        Type responseType = getCallResponseType(returnType);
        Executor callbackExecutor = retrofit.callbackExecutor();
        return new HttpCallAdapter<>(responseType, callbackExecutor);
    }

    private Type getCallResponseType(Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Call return type must be parameterized as HttpCall<Foo> or HttpCall<? extends Foo>");
        }
        return getParameterUpperBound(0, (ParameterizedType) returnType);
    }
}

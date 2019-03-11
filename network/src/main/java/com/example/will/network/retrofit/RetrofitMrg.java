package com.example.will.network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMrg {
    private final String baseUrl = "http://localhost:8080//";
    private final String userUrl = "restful//user.jsp";
    private final String songUrl = "restful//song.jsp";
    private final String songListUrl = "restful//songList.jsp";
    private final String commentUrl = "restful//comment.jsp";

    private static RetrofitMrg INSTANCE;

    private Retrofit retrofit;

    public RetrofitMrg() {
        initRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(new HttpCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitMrg getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (RetrofitMrg.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitMrg();
                }
            }
        }
        return INSTANCE;
    }
}

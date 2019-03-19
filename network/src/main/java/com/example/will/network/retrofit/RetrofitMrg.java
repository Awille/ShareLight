package com.example.will.network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMrg {
    private final String baseUrl = "http://47.107.173.110:8080/MusicBackground/";
    public static final String userUrl = "restful/user.jsp";
    public static final String songUrl = "restful/song.jsp";
    public static final String songListUrl = "restful/songList.jsp";
    public static final String commentUrl = "restful/comment.jsp";

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

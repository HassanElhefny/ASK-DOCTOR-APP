package com.elhefny.askdoctor.LastNews;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClinet {
    public static String myKey = "47443709c0de430ea35c156c425ee6ca";
    private static String baseURl = "https://newsapi.org";

    private static Retrofit getInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NewsInterface getServices() {
        return getInstance().create(NewsInterface.class);
    }
}

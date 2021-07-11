package com.elhefny.askdoctor.Covid_19_API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Covid_19_Client {
    private static String BaseUrl = "https://api.covid19api.com";
    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static Covid_19_Interface getService(){
        return getRetrofitInstance().create(Covid_19_Interface.class);
    }
}

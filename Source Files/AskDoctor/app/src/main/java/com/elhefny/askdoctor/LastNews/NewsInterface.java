package com.elhefny.askdoctor.LastNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {
    @GET("v2/top-headlines")
    Call<NewNews> getNews(@Query("country") String country,
                          @Query("Category") String Category,
                          @Query("apiKey") String key);
}

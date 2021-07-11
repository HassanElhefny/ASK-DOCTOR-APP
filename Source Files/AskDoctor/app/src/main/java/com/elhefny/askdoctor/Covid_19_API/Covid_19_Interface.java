package com.elhefny.askdoctor.Covid_19_API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Covid_19_Interface {
    @GET("summary")
    Call<CountriesData> getData();
}

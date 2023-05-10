package com.data.trucksharingapp.api;

import com.data.trucksharingapp.util.Important;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfiguration {
    public ApiInterface getApi(){
        // using retrofit library to implement ApiInterface
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com")
                .build();
        return builder.create(ApiInterface.class);
    }
}

package com.data.trucksharingapp.api;

import com.data.trucksharingapp.chatbot.ResultDistance;
import com.data.trucksharingapp.picklocation.Result;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

// using Retrofit
// defines the HTTP operation
// resource: https://medium.com/android-news/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb
public interface ApiInterface {

    // distance matrix to get the dinstance travelled from pickup loc to dropoff loc
    @GET("maps/api/distancematrix/json")
    Single<ResultDistance> getDistance(
            @Query("key") String key,
            @Query("origins") String origin,
            @Query("destinations") String destination
    );
}

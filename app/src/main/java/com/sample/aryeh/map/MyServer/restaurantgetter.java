package com.sample.aryeh.map.MyServer;

import com.sample.aryeh.map.MyServer.Restaurant.Restaurant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aryeh on 7/14/2017.
 */

public interface restaurantgetter {
    @GET("/")
    Observable<List<Restaurant>> getRestaurants(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude/*,
            @Query("max") int max,
            @Query("maxdistance") int maxdistance*/
    );
}

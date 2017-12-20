package com.sample.aryeh.map.YelpFusionApi;

/**
 * Created by aryeh on 8/14/2017.
 */

import com.yelp.fusion.client.models.AutoComplete;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Reviews;
import com.yelp.fusion.client.models.SearchResponse;
import io.reactivex.Observable;


import java.util.Map;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Ranga on 2/22/2017.
 */

public interface YelpFusionApi {
    @GET("/v3/businesses/search")
    Observable<SearchResponse> getBusinessSearch(@QueryMap Map<String, String> params);

    @GET("/v3/businesses/search/phone")
    Observable<SearchResponse> getPhoneSearch(@Query("phone") String phone);

    @GET("/v3/transactions/{transaction_type}/search")
    Observable<SearchResponse> getTransactionSearch(@Path("transaction_type") String transactionType, @QueryMap Map<String, String> params);

    @GET("/v3/businesses/{id}")
    Observable<Business> getBusiness(@Path("id") String id);

    @GET("/v3/businesses/{id}/reviews")
    Observable<Reviews> getBusinessReviews(@Path("id") String id, @Query("locale") String locale);

    @GET("/v3/autocomplete")
    Observable<AutoComplete> getAutocomplete(@QueryMap Map<String, String> params);

}
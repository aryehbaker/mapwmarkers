package com.sample.aryeh.map.MyServer;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aryeh on 8/3/2017.
 */

public class MyServerRetrofitHelper {
    private static final MyServerRetrofitHelper our_instance = new MyServerRetrofitHelper();
    static MyServerRetrofitHelper getMSRHInstance(){return our_instance;    }
    private static final String API_BASE_URL = "http://34.227.12.12:3000";
    private static MyServerRetrofitHelper mHelper;
    private Retrofit mRetrofit;
    private MyServerRetrofitHelper() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        mRetrofit = builder.client(httpClient.build()).build();
    }




        restaurantgetter client =  mRetrofit.create(restaurantgetter.class);

    }


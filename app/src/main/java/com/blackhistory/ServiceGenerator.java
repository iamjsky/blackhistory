package com.blackhistory;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 2017. 11. 7..
 */

public class ServiceGenerator {

    public static Retrofit retrofit;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()

//                .baseUrl("http://192.168.0.18:8080/")
                .baseUrl("http://www.soundleader.co.kr/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();



    }

    public static <T> T generate(Class<T> clazz) {
        return retrofit.create(clazz);
    }



}

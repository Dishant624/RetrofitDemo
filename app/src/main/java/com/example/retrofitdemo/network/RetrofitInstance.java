package com.example.retrofitdemo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;

    private static String baseUrl="http://navjacinth9.000webhostapp.com/json/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

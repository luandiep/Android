package com.example.quanlyxe.RestApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiCient {
    public  static  final String BASE_URL="http://saleman.haitriet.com/";
    private static Retrofit retrofit = null;




public  static  Retrofit getClient() {
    if (retrofit == null) {
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        // OkHttp de tao request client-server va add authenticator, interceptors, dispatchers
        OkHttpClient okClient = new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MINUTES)
                .writeTimeout(1000, TimeUnit.MINUTES)
                .readTimeout(1500, TimeUnit.MINUTES)
                .authenticator(tokenAuthenticator)
                .dispatcher(dispatcher)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okClient)
                .build();

    }
    return retrofit;


}

}

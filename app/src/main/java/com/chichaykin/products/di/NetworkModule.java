package com.chichaykin.products.di;


import com.chichaykin.products.BuildConfig;
import com.chichaykin.products.network.NetworkApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class NetworkModule {

    private static final String URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com";

    @Provides
    @Singleton
    public NetworkApi buildNetworkApi() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.interceptors().add(logging);
        }

        return new Retrofit.Builder().client(clientBuilder.build())
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkApi.class);

    }
}

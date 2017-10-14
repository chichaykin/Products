package com.chichaykin.products.di;

import android.content.Context;
import com.chichaykin.products.network.ConnectivityHelper;
import com.chichaykin.products.network.NetworkApi;
import com.chichaykin.products.ui.main.MainPresenter;
import com.chichaykin.products.ui.main.MainPresenterImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ConnectivityHelper provideConnectivityHelper() {
        return new ConnectivityHelper(context);
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(NetworkApi api, ConnectivityHelper helper) {
        return new MainPresenterImpl(api, helper);
    }
}

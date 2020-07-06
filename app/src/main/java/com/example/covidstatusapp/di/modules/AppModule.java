package com.example.covidstatusapp.di.modules;

import android.app.Application;

import com.example.covidstatusapp.ui.utils.Constants;
import com.example.covidstatusapp.ui.utils.sharedPreferences.AppPreferenceManagerHelper;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(setLogging())
                .build();
    }


    @Provides
    @Singleton
    static OkHttpClient setLogging() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Named("PREF_NAME")
    String providePreferenceName() {
        return Constants.prefName;
    }


    @Provides
    @Singleton
    AppPreferenceManagerHelper providePreferenceManager(Application context, @Named("PREF_NAME") String prefName) {
        return new PreferenceManager(context, prefName);
    }


}

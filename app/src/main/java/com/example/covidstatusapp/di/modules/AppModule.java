package com.example.covidstatusapp.di.modules;

import android.app.Application;

import com.example.covidstatusapp.ui.utils.Constants;
import com.example.covidstatusapp.ui.utils.sharedPreferences.AppPreferenceManagerHelper;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class AppModule {
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

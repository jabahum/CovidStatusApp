package com.example.covidstatusapp.di.modules.main;


import android.app.Application;

import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    // Months
    @MainScope
    @Provides
    static ArrayList<String> provideListOfMonths() {
        return new ArrayList<>(Arrays.asList(new DateFormatSymbols().getShortMonths()));
    }

    @MainScope
    @Provides
    static PreferenceManager providerPreferenceManger(Application application, @Named("PREF_NAME") String prefname) {
        return new PreferenceManager(application, prefname);
    }

}

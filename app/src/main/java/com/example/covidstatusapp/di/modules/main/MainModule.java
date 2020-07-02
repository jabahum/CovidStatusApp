package com.example.covidstatusapp.di.modules.main;


import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui.utils.Constants;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder
                .baseUrl(Constants.base_Url)
                .build()
                .create(MainApi.class);
    }

    // Months
    @MainScope
    @Provides
    static ArrayList<String> provideListOfMonths() {
        return new ArrayList<>(Arrays.asList(new DateFormatSymbols().getShortMonths()));
    }

}

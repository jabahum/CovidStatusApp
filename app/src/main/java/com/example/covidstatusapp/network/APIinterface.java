package com.example.covidstatusapp.network;

import com.example.covidstatusapp.models.LiveCases;
import com.example.covidstatusapp.models.CountryChartModel;
import com.example.covidstatusapp.models.SummaryResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIinterface {

    @GET("live/country/{country}")
    Single<List<LiveCases>> getCountryCases(@Path("country") String country );

    @GET("summary")
    Single<SummaryResponse> getSummary();

    @GET("/total/country/{country}")
    Single<List<CountryChartModel>> getCountryChartData(@Path("country") String country);
}

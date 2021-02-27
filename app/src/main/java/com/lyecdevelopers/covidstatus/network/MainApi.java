package com.lyecdevelopers.covidstatus.network;

import com.lyecdevelopers.covidstatus.ui.models.CountryChartModel;
import com.lyecdevelopers.covidstatus.ui.models.LiveCases;
import com.lyecdevelopers.covidstatus.ui.models.SummaryResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainApi {
    @GET("live/country/{country}")
    Single<List<LiveCases>> getCountryCases(@Path("country") String country);

    @GET("summary")
    Single<SummaryResponse> getSummary();

    @GET("/total/country/{country}")
    Single<List<CountryChartModel>> getCountryChartData(@Path("country") String country);
}

package com.example.covidstatusapp.network;

import com.example.covidstatusapp.dashboard.models.LiveCases;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIinterface {

    @GET("live/country/uganda")
    Single<LiveCases> getLiveCases();

}

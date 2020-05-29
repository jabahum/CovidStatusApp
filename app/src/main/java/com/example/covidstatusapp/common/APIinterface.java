package com.example.covidstatusapp.common;

import com.example.covidstatusapp.dashboard.models.ConfirmedCases;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.models.LiveCases;

import java.util.List;

import   io.reactivex.Observable;

import retrofit2.http.GET;

public interface APIinterface {

    @GET("/live/country/uganda/status/confirmed")
    Observable<List<ConfirmedCases>> getConfirmedCase();

    @GET("live/country/uganda")
    Observable<List<LiveCases>> getLiveCases();

    @GET("countries")
    Observable<List<Countries>> getCountries();

}

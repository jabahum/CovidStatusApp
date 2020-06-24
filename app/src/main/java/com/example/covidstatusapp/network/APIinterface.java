package com.example.covidstatusapp.network;

import com.example.covidstatusapp.countrydetails.models.CountryAllStatus;
import com.example.covidstatusapp.dashboard.models.ConfirmedCases;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.models.LiveCases;

import java.util.List;

import   io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("/live/country/uganda/status/confirmed")
    Observable<List<ConfirmedCases>> getConfirmedCase();

    @GET("live/country/uganda")
    Observable<List<LiveCases>> getLiveCases();

    @GET("countries")
    Observable<List<Countries>> getCountries();
    //https://api.covid19api.com/country/south-africa?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z


    @GET("/country/{country}")
    Observable<List<CountryAllStatus>> getCountryAllStatus(@Path("country") String country,
                                                           @Query("from") String from,@Query("to") String to);

}

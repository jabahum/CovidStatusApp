/*
package com.example.covidstatusapp.dashboard.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.repositories.CountriesRepository;

import java.util.List;

public class CountriesViewModel extends ViewModel {

    private MutableLiveData<List<Countries>> mutableLiveData;
    private CountriesRepository countriesRepository;


    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        countriesRepository = CountriesRepository.getInstance();
        mutableLiveData = countriesRepository.getCountries();

    }

    public LiveData<List<Countries>> getCountriesRepository() {
        return mutableLiveData;
    }
}
*/

package com.example.covidstatusapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.countrydetails.models.CountryAllStatus;
import com.example.covidstatusapp.repositories.CountryAllStatusRepository;

import java.util.List;

public class CountryAllStatusViewModel extends ViewModel {
    private MutableLiveData<List<CountryAllStatus>> mutableLiveData;
    private CountryAllStatusRepository countryAllStatusRepository;


    public void init(String country,String from,String to) {
        if (mutableLiveData != null) {
            return;
        }
        countryAllStatusRepository = CountryAllStatusRepository.getInstance();
        mutableLiveData = countryAllStatusRepository.getCountryAllStatus(country,from,to);

    }

    public LiveData<List<CountryAllStatus>> getCountryAllStatusRepository() {
        return mutableLiveData;
    }

}

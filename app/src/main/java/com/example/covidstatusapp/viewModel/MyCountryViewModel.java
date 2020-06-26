package com.example.covidstatusapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.dashboard.models.LiveCases;
import com.example.covidstatusapp.repositories.MyCountryRepository;
import com.example.covidstatusapp.utils.Resource;

import java.util.List;

public class MyCountryViewModel extends ViewModel {

    private MutableLiveData<Resource<List<LiveCases>>> mutableLiveData;
    private MyCountryRepository myCountryRepository;

    public void init(String country) {
        if (mutableLiveData != null) {
            return;
        }
        myCountryRepository = MyCountryRepository.getInstance();
        mutableLiveData = myCountryRepository.getMyCountry(country);

    }

    public LiveData<Resource<List<LiveCases>>> getMyCountryRepository() {
        return mutableLiveData;
    }
}

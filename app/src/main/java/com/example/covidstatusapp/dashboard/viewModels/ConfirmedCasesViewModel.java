/*
package com.example.covidstatusapp.dashboard.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.dashboard.models.ConfirmedCases;
import com.example.covidstatusapp.dashboard.repositories.ConfirmedCasesRepository;

import java.util.List;

public class ConfirmedCasesViewModel extends ViewModel {

    private MutableLiveData<List<ConfirmedCases>> mutableLiveData;
    private ConfirmedCasesRepository confirmedCasesRepository;


    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        confirmedCasesRepository = ConfirmedCasesRepository.getInstance();
        mutableLiveData = confirmedCasesRepository.getConfirmedCases();

    }

    public LiveData<List<ConfirmedCases>> getConfirmedCasesRepository() {
        return mutableLiveData;
    }
}
*/

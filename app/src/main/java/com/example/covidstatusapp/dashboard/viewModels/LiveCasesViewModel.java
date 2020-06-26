/*
package com.example.covidstatusapp.dashboard.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.dashboard.models.LiveCases;
import com.example.covidstatusapp.dashboard.repositories.LiveCasesRepository;

import java.util.List;

public class LiveCasesViewModel extends ViewModel {
    private MutableLiveData<List<LiveCases>> mutableLiveData;
    private LiveCasesRepository liveCasesRepository;


    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        liveCasesRepository = LiveCasesRepository.getInstance();
        mutableLiveData = liveCasesRepository.getLiveCases();

    }

    public LiveData<List<LiveCases>> getLiveCasesRepository() {
        return mutableLiveData;
    }
}
*/

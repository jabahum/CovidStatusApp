package com.example.covidstatusapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.models.SummaryResponse;
import com.example.covidstatusapp.repositories.GlobalRepository;
import com.example.covidstatusapp.utils.Resource;

public class GlobalViewModel extends ViewModel {
    private MutableLiveData<Resource<SummaryResponse>> mutableLiveData;
    private GlobalRepository globalRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        globalRepository = GlobalRepository.getInstance();
        mutableLiveData = globalRepository.getGlobal();

    }

    public LiveData<Resource<SummaryResponse>> getGlobalRepository() {
        return mutableLiveData;
    }
}

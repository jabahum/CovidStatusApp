package com.example.covidstatusapp.viewModel;

import androidx.lifecycle.ViewModel;

public class GlobalViewModel extends ViewModel {
    private MutableLiveData<Resource<MainModel>> mutableLiveData;
    private TopTracksRepository topTracksRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        topTracksRepository = TopTracksRepository.getInstance();
        mutableLiveData = topTracksRepository.getTopTracks();

    }

    public LiveData<Resource<MainModel>> getTopTracksRepository() {
        return mutableLiveData;
    }
}

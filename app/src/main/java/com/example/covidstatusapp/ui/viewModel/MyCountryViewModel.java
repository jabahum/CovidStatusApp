package com.example.covidstatusapp.ui.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui._base.BaseViewModel;
import com.example.covidstatusapp.ui.models.LiveCases;
import com.example.covidstatusapp.ui.utils.Resource;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MyCountryViewModel extends BaseViewModel {

    private MediatorLiveData<Resource<List<LiveCases>>> data;
    private MainApi mainApi;
    private final PreferenceManager preferenceManager;

    @Inject
    MyCountryViewModel(MainApi mainApi, PreferenceManager preferenceManager) {
        this.mainApi = mainApi;
        this.preferenceManager = preferenceManager;
    }

    public LiveData<Resource<List<LiveCases>>> getMyCountry() {

        if (data == null) {
            data = new MediatorLiveData<>();
            data.setValue(Resource.loading(null));

            final LiveData<Resource<List<LiveCases>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getCountryCases(preferenceManager.getSelectedCountry())
                            .toFlowable()
                            .onErrorReturn(throwable -> {
                                LiveCases liveCases = new LiveCases();
                                liveCases.setDeaths(1);
                                ArrayList<LiveCases> list = new ArrayList<>();
                                list.add(liveCases);
                                return list;
                            })
                            .map((Function<List<LiveCases>, Resource<List<LiveCases>>>) liveCases -> {
                                if (liveCases.size() > 0) {
                                    if (liveCases.get(0).getDeaths() == 1) {
                                        return Resource.error("Something Went Wrong", null);
                                    }
                                }
                                return Resource.success(liveCases);
                            }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );

            data.addSource(source, liveCasesResource -> {
                data.setValue(liveCasesResource);
                data.removeSource(source);
            });
        }

        return data;
    }
}

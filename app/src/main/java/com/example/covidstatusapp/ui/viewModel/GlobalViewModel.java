package com.example.covidstatusapp.ui.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui._base.BaseViewModel;
import com.example.covidstatusapp.ui.models.SummaryResponse;
import com.example.covidstatusapp.ui.utils.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class GlobalViewModel extends BaseViewModel {

    private MainApi mainApi;
    private MediatorLiveData<Resource<SummaryResponse>> data;

    @Inject
    GlobalViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    public LiveData<Resource<SummaryResponse>> getGlobal() {

        if (data == null) {
            data = new MediatorLiveData<>();
            data.setValue(Resource.loading(null));

            final LiveData<Resource<SummaryResponse>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getSummary()
                            .toFlowable()
                            .onErrorReturn(throwable -> new SummaryResponse())
                            .map((Function<SummaryResponse, Resource<SummaryResponse>>) summaryResponse -> {
                                if (summaryResponse == null) {
                                    return Resource.error("Error Couldn't Fetch Data", null);
                                }
                                return Resource.success(summaryResponse);
                            }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );

            data.addSource(source, mainModelResource -> {
                data.setValue(mainModelResource);
                data.removeSource(source);
            });
        }


        return data;
    }
}

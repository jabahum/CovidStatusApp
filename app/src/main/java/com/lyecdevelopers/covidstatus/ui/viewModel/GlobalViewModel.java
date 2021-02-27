package com.lyecdevelopers.covidstatus.ui.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.lyecdevelopers.covidstatus.network.MainApi;
import com.lyecdevelopers.covidstatus.ui._base.BaseViewModel;
import com.lyecdevelopers.covidstatus.ui.models.SummaryResponse;
import com.lyecdevelopers.covidstatus.ui.utils.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class GlobalViewModel extends BaseViewModel {

    private final MainApi mainApi;
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
                            .onErrorReturn(throwable -> {
                                SummaryResponse responseError = new SummaryResponse();
                                responseError.setDate("-1");
                                return responseError;
                            })
                            .map((Function<SummaryResponse, Resource<SummaryResponse>>) summaryResponse -> {
                                if (summaryResponse == null || summaryResponse.getDate().equals("-1")) {
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

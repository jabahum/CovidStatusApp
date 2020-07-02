/*
package com.example.covidstatusapp.ui.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.example.covidstatusapp.ui.models.SummaryResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;

public class GlobalRepository {
    private static GlobalRepository globalRepository;
    private APIinterface apIinterface;

    private MediatorLiveData<Resource<SummaryResponse>> data = new MediatorLiveData<>();

    private GlobalRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static GlobalRepository getInstance() {
        if (globalRepository == null) {
            globalRepository = new GlobalRepository();
        }
        return globalRepository;
    }


    public MediatorLiveData<Resource<SummaryResponse>> getGlobal() {

        data.setValue(Resource.loading(null));

        final LiveData<Resource<SummaryResponse>> source = LiveDataReactiveStreams.fromPublisher(
                apIinterface.getSummary()
                        .toFlowable()
                        .onErrorReturn(throwable -> new SummaryResponse())
                        .map((Function<SummaryResponse, Resource<SummaryResponse>>) summaryResponse -> {
                            if (summaryResponse == null ) {
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

        return data;
    }

}
*/

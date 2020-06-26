package com.example.covidstatusapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.covidstatusapp.common.Resource;
import com.example.covidstatusapp.models.SummaryResponse;
import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GlobalRepository {
    private static GlobalRepository globalRepository;
    private APIinterface apIinterface;

    private MediatorLiveData<Resource<SummaryResponse>> data = new MediatorLiveData<>();

    public GlobalRepository() {
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

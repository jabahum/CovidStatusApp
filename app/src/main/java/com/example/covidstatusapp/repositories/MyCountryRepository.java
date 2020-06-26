package com.example.covidstatusapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.covidstatusapp.common.Resource;
import com.example.covidstatusapp.dashboard.models.LiveCases;
import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyCountryRepository {
    private static MyCountryRepository myCountryRepository;
    private APIinterface apIinterface;

    private MediatorLiveData<Resource<List<LiveCases>>> data = new MediatorLiveData<>();

    public MyCountryRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static MyCountryRepository getInstance() {
        if (myCountryRepository == null) {
            myCountryRepository = new MyCountryRepository();
        }
        return myCountryRepository;
    }

    public MediatorLiveData<Resource<List<LiveCases>>> getMyCountry(String country) {

        data.setValue(Resource.loading(null));

        final LiveData<Resource<List<LiveCases>>> source = LiveDataReactiveStreams.fromPublisher(
                apIinterface.getCountryCases(country)
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

        return data;
    }


}

/*
package com.example.covidstatusapp.dashboard.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;
import com.example.covidstatusapp.dashboard.models.ConfirmedCases;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConfirmedCasesRepository {

    private static ConfirmedCasesRepository confirmedCasesRepository;
    private APIinterface apIinterface;

    public ConfirmedCasesRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static ConfirmedCasesRepository getInstance() {
        if (confirmedCasesRepository == null) {
            confirmedCasesRepository = new ConfirmedCasesRepository();
        }
        return confirmedCasesRepository;
    }

    public MutableLiveData<List<ConfirmedCases>> getConfirmedCases() {
        final MutableLiveData<List<ConfirmedCases>> confirmedCasesLiveData = new MutableLiveData<>();

        apIinterface.getConfirmedCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ConfirmedCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ConfirmedCases> confirmedCases) {
                        if (confirmedCases != null) {
                            confirmedCasesLiveData.setValue(confirmedCases);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return confirmedCasesLiveData;
    }
}
*/

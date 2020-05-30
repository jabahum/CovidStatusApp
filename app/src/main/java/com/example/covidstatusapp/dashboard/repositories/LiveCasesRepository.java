package com.example.covidstatusapp.dashboard.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.covidstatusapp.common.APIClient;
import com.example.covidstatusapp.common.APIinterface;
import com.example.covidstatusapp.dashboard.models.LiveCases;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LiveCasesRepository {

    private static LiveCasesRepository liveCasesRepository;
    private APIinterface apIinterface;

    public LiveCasesRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static LiveCasesRepository getInstance() {
        if (liveCasesRepository == null) {
            liveCasesRepository = new LiveCasesRepository();
        }
        return liveCasesRepository;
    }

    public MutableLiveData<List<LiveCases>> getLiveCases() {
        final MutableLiveData<List<LiveCases>> liveCasesLiveData = new MutableLiveData<>();

        apIinterface.getLiveCases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LiveCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LiveCases> liveCases) {
                        if (liveCases != null) {
                            liveCasesLiveData.setValue(liveCases);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return liveCasesLiveData;
    }
}

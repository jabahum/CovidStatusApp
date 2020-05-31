package com.example.covidstatusapp.countrydetails.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.covidstatusapp.common.APIClient;
import com.example.covidstatusapp.common.APIinterface;
import com.example.covidstatusapp.countrydetails.models.CountryAllStatus;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CountryAllStatusRepository {

    private static CountryAllStatusRepository countryAllStatusRepository;
    private APIinterface apIinterface;

    public CountryAllStatusRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static CountryAllStatusRepository getInstance() {
        if (countryAllStatusRepository == null) {
            countryAllStatusRepository = new CountryAllStatusRepository();
        }
        return countryAllStatusRepository;
    }

    public MutableLiveData<List<CountryAllStatus>> getCountryAllStatus(String country, String from, String to) {
        final MutableLiveData<List<CountryAllStatus>> countryAllStatusLiveData = new MutableLiveData<>();

        apIinterface.getCountryAllStatus(country, from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CountryAllStatus>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CountryAllStatus> countryAllStatuses) {
                        if (countryAllStatuses != null){
                            Collections.reverse(countryAllStatuses);
                            countryAllStatusLiveData.setValue(countryAllStatuses);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return countryAllStatusLiveData;

    }
}

package com.example.covidstatusapp.dashboard.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;
import com.example.covidstatusapp.dashboard.models.Countries;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CountriesRepository {
    private static CountriesRepository countriesRepository;
    private APIinterface apIinterface;

    public CountriesRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static CountriesRepository getInstance() {
        if (countriesRepository == null) {
            countriesRepository = new CountriesRepository();
        }
        return countriesRepository;
    }

    public MutableLiveData<List<Countries>> getCountries() {
        final MutableLiveData<List<Countries>> countriesLiveData = new MutableLiveData<>();
        apIinterface.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Countries>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Countries> countries) {
                        if (countries != null) {
                            countriesLiveData.setValue(countries);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return countriesLiveData;
    }
}

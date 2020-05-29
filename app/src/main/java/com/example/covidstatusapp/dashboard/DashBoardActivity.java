package com.example.covidstatusapp.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.APIClient;
import com.example.covidstatusapp.common.APIinterface;
import com.example.covidstatusapp.dashboard.models.ConfirmedCases;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.models.LiveCases;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DashBoardActivity extends AppCompatActivity {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    APIinterface apIinterface;
    List<Countries> mCountries;
    Spinner spinner;
    ArrayAdapter<String> countriesArrayAdapter;
    ArrayList<String> countriesList;

    public List<Countries> getmCountries() {
        return mCountries;
    }

    public void setmCountries(List<Countries> mCountries) {
        this.mCountries = mCountries;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        spinner = findViewById(R.id.sp_countries);

        mCountries = new ArrayList<>();
        countriesList = new ArrayList<>();
        apIinterface = APIClient.getClient().create(APIinterface.class);

        getConfirmedCases();
        getCountries();
        getLiveCases();

        countriesArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countriesList);
        spinner.setAdapter(countriesArrayAdapter);

    }

    private void getConfirmedCases() {
        apIinterface.getConfirmedCase().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ConfirmedCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ConfirmedCases> confirmedCases) {

                        Timber.i(TAG, confirmedCases.toString());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getLiveCases() {
        apIinterface.getLiveCases().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LiveCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LiveCases> liveCases) {

                        Timber.i(TAG, liveCases.toString());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getCountries() {
        apIinterface.getCountries().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Countries>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Countries> countries) {

                        Timber.i(TAG, countries.toString());
                        for (Countries country : countries){
                            countriesList.add(country.getCountry());
                        }
                        setmCountries(countries);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

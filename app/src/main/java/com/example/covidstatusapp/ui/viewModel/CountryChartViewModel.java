package com.example.covidstatusapp.ui.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui._base.BaseViewModel;
import com.example.covidstatusapp.ui.models.ChartModel;
import com.example.covidstatusapp.ui.models.CountryChartModel;
import com.example.covidstatusapp.ui.utils.CommonUtils;
import com.example.covidstatusapp.ui.utils.Resource;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CountryChartViewModel extends BaseViewModel {

    private final MainApi mainApi;
    private final PreferenceManager preferenceManager;
    ChartModel chartModel;
    private MediatorLiveData<Resource<List<CountryChartModel>>> data;
    private MediatorLiveData<Resource<ChartModel>> chartData;

    @Inject
    CountryChartViewModel(MainApi mainApi, PreferenceManager preferenceManager) {
        this.mainApi = mainApi;
        this.preferenceManager = preferenceManager;
    }


    // Chart
    public LiveData<Resource<ChartModel>> observeChartData() {

        if (chartData == null) {
            chartData = new MediatorLiveData<>();
            chartData.setValue(Resource.loading(null));

            List<BarEntry> confirmedBarEntry = new ArrayList<>();
            List<BarEntry> deathsBarEntry = new ArrayList<>();
            List<BarEntry> recoveredBarEntry = new ArrayList<>();

            String[] months = new DateFormatSymbols().getShortMonths();

            ArrayList<String> monthsShort = new ArrayList<>(Arrays.asList(months).subList(0, months.length - 1));

            // Initialize Data Sets
            for (int i = 1; i <= monthsShort.size(); i++) {
                confirmedBarEntry.add(new BarEntry(i, 0));
                deathsBarEntry.add(new BarEntry(i,0));
                recoveredBarEntry.add(new BarEntry(i,0));
            }

            ChartModel tempChartModel = new ChartModel(confirmedBarEntry,deathsBarEntry,recoveredBarEntry);

            LiveData<Resource<List<CountryChartModel>>> countrySource =  getCountryChartModel();

            chartData.addSource(countrySource, listResource -> {
                if (listResource.status == Resource.Status.ERROR || listResource.status == Resource.Status.SUCCESS) {
                    if (listResource.status == Resource.Status.SUCCESS) {
                        if (listResource.data != null) {
                            for (CountryChartModel countryChartModel : listResource.data) {
                                BarEntry barEntry = confirmedBarEntry.get(CommonUtils.month(countryChartModel.getDate()));
                                barEntry.setY(barEntry.getY() + countryChartModel.getConfirmed());
                                confirmedBarEntry.set(CommonUtils.month(countryChartModel.getDate()), barEntry);

                            }
                            tempChartModel.setConfirmed(confirmedBarEntry);

                            for (CountryChartModel countryChartModel : listResource.data) {
                                BarEntry barEntry = deathsBarEntry.get(CommonUtils.month(countryChartModel.getDate()));
                                barEntry.setY(barEntry.getY() + countryChartModel.getDeaths());
                                deathsBarEntry.set(CommonUtils.month(countryChartModel.getDate()), barEntry);

                            }

                            tempChartModel.setDeaths(deathsBarEntry);
                            for (CountryChartModel countryChartModel : listResource.data) {
                                BarEntry barEntry = recoveredBarEntry.get(CommonUtils.month(countryChartModel.getDate()));
                                barEntry.setY(barEntry.getY() + countryChartModel.getRecovered());
                                recoveredBarEntry.set(CommonUtils.month(countryChartModel.getDate()), barEntry);

                            }

                            tempChartModel.setRecovered(recoveredBarEntry);
                        }

                    }
                    chartData.removeSource(countrySource);


                    chartModel = tempChartModel;

                    chartData.setValue(Resource.success(tempChartModel));

                }
            });

        }

        return chartData;
    }


    LiveData<Resource<List<CountryChartModel>>> getCountryChartModel() {

        if (data == null) {
            data = new MediatorLiveData<>();
            data.setValue(Resource.loading(null));

            final LiveData<Resource<List<CountryChartModel>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getCountryChartData(preferenceManager.getSelectedCountry())
                            .toFlowable()
                            .onErrorReturn(throwable -> {
                                CountryChartModel chartModel = new CountryChartModel();
                                chartModel.setActive(1);
                                ArrayList<CountryChartModel> list = new ArrayList<>();
                                list.add(chartModel);
                                return list;
                            })
                            .map((Function<List<CountryChartModel>, Resource<List<CountryChartModel>>>) chartModelList -> {
                                if (chartModelList.size() > 0) {
                                    if (chartModelList.get(0).getActive() == 1) {
                                        return Resource.error("Something Went Wrong", null);
                                    }
                                }
                                return Resource.success(chartModelList);
                            }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );


            data.addSource(source, liveCasesResource -> {
                data.setValue(liveCasesResource);
                data.removeSource(source);
            });
        }

        return data;
    }

}

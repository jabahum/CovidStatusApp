package com.example.covidstatusapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.example.covidstatusapp.models.ChartModel;
import com.example.covidstatusapp.models.CountryChartModel;
import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;
import com.example.covidstatusapp.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CountryChartRepository {
    private static CountryChartRepository countryChartRepository;
    private APIinterface apIinterface;
    private ChartModel chartModel;

    private MediatorLiveData<Resource<List<CountryChartModel>>> data = new MediatorLiveData<>();
    private MediatorLiveData<Resource<ChartModel>> chartData;


    private CountryChartRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static CountryChartRepository getInstance() {
        if (countryChartRepository == null) {
            countryChartRepository = new CountryChartRepository();
        }
        return countryChartRepository;
    }


    public MediatorLiveData<Resource<List<CountryChartModel>>> getCountryChartModel(String country) {

        data.setValue(Resource.loading(null));

        final LiveData<Resource<List<CountryChartModel>>> source = LiveDataReactiveStreams.fromPublisher(
                apIinterface.getCountryChartData(country)
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
                                if (chartModelList.get(0).getDeaths() == 1) {
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

        return data;
    }


    // Chart
/*
    public LiveData<Resource<ChartModel>> observeChartData() {

        if (chartData == null) {
            chartData = new MediatorLiveData<>();
            chartData.setValue(Resource.loading(null));

            List<BarEntry> confirmedBarEntry = new ArrayList<>();


            // Initialize Data Sets
            for (int i = 1; i <= months.size(); i++) {
                confirmedBarEntry.add(new BarEntry(i, 0));
            }

            ChartModel tempChartModel = new ChartModel(confirmedBarEntry);

            LiveData<Resource<List<CountryChartModel>>> countryChartModel = getCountryChartModel();


            chartData.addSource(countryChartModel, countryResource -> {
                if (countryResource.status == Resource.Status.ERROR || countryResource.status == Resource.Status.SUCCESS) {
                    if (countryResource.status == Resource.Status.SUCCESS) {
                        for (CountryChartModel chartModel : countryResource.data) {
                            BarEntry barEntry = confirmedBarEntry.get(chartModel.time().getMonthValue() - 1);
                            barEntry.setY(barEntry.getY() + chartModel.getConfirmed());

                            confirmedBarEntry.set(customIncome.time().getMonthValue() - 1, barEntry);
                        }
                        tempChartModel.setIncome(confirmedBarEntry);
                    }

                    chartData.removeSource(countryResource);

                    chartModel = tempChartModel;
                    chartData.setValue(Resource.success(tempChartModel));
                }

            });

        }

        return chartData;
    }
*/


}

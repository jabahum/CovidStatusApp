package com.example.covidstatusapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.models.ChartModel;
import com.example.covidstatusapp.models.CountryChartModel;
import com.example.covidstatusapp.repositories.CountryChartRepository;
import com.example.covidstatusapp.utils.CommonUtils;
import com.example.covidstatusapp.utils.Resource;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CountryChartViewModel extends ViewModel {
    private MutableLiveData<Resource<List<CountryChartModel>>> mutableLiveData;
    private CountryChartRepository countryChartRepository;
    private ChartModel chartModel;

    // Records
    private MediatorLiveData<Resource<ChartModel>> chartData;

    public void init(String country) {
        if (mutableLiveData != null) {
            return;
        }
        countryChartRepository = CountryChartRepository.getInstance();
        mutableLiveData = countryChartRepository.getCountryChartModel(country);

    }

    public LiveData<Resource<List<CountryChartModel>>> getCountryChartRepository() {
        return mutableLiveData;
    }


    // Chart
    public LiveData<Resource<ChartModel>> observeChartData() {

        if (chartData == null) {
            chartData = new MediatorLiveData<>();
            chartData.setValue(Resource.loading(null));

            List<BarEntry> confirmedBarEntry = new ArrayList<>();
            List<BarEntry> deathsBarEntry = new ArrayList<>();
            List<BarEntry> recoveredBarEntry = new ArrayList<>();

            ArrayList<String> monthsShort = new ArrayList<>();

            String[] months = new DateFormatSymbols().getShortMonths();

            for (int i = 0; i < months.length - 1; i++) {
                String month = months[i];
                System.out.println("Month [" + i + "] = " + month);
                monthsShort.add(month);
            }

            // Initialize Data Sets
            for (int i = 1; i <= monthsShort.size(); i++) {
                confirmedBarEntry.add(new BarEntry(i, 0));
                deathsBarEntry.add(new BarEntry(i,0));
                recoveredBarEntry.add(new BarEntry(i,0));
            }

            ChartModel tempChartModel = new ChartModel(confirmedBarEntry,deathsBarEntry,recoveredBarEntry);

            LiveData<Resource<List<CountryChartModel>>> countrySource = getCountryChartRepository();

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

}

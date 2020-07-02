package com.example.covidstatusapp.ui.models;


import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class ChartModel {

    private List<BarEntry> confirmed;

    private List<BarEntry> deaths;

    private List<BarEntry> recovered;

    private String error;

    public ChartModel(List<BarEntry> confirmed, List<BarEntry> deaths, List<BarEntry> recovered) {
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public List<BarEntry> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<BarEntry> deaths) {
        this.deaths = deaths;
    }

    public List<BarEntry> getRecovered() {
        return recovered;
    }

    public void setRecovered(List<BarEntry> recovered) {
        this.recovered = recovered;
    }

    public ChartModel(String error) {
        this.error = error;
    }

    public List<BarEntry> getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(List<BarEntry> confirmed) {
        this.confirmed = confirmed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
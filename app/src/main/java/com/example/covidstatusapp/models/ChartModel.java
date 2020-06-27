package com.example.covidstatusapp.models;

import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class ChartModel {

    private List<BarEntry> confirmed;

    private String error;

    public ChartModel(String error) {
        this.error = error;
    }

    public ChartModel(List<BarEntry> confirmed) {
        this.confirmed = confirmed;
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
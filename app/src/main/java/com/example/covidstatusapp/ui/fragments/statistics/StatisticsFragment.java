package com.example.covidstatusapp.ui.fragments.statistics;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.ViewModelProviderFactory;
import com.example.covidstatusapp.databinding.StatisticsFragmentBinding;
import com.example.covidstatusapp.ui._base.BaseFragment;
import com.example.covidstatusapp.ui.adapters.CustomPagerAdapter;
import com.example.covidstatusapp.ui.models.ChartModel;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;
import com.example.covidstatusapp.ui.viewModel.CountryChartViewModel;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class StatisticsFragment extends BaseFragment<StatisticsFragmentBinding, CountryChartViewModel> {

    StatisticsFragmentBinding binding;

    private CountryChartViewModel chartViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    //@Inject
    ArrayList<String> monthsShort;


    @Inject
    PreferenceManager preferenceManager;


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.statistics_fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = getViewDataBinding();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        subscriberObservers();
    }

    @Override
    public CountryChartViewModel getViewModel() {
        chartViewModel = new ViewModelProvider(getBaseActivity(), providerFactory).get(CountryChartViewModel.class);
        return chartViewModel;
    }

    @Override
    public void onResume() {
        subscriberObservers();
        super.onResume();
    }

    private void subscriberObservers() {
        chartViewModel.getChartData().removeObservers(getViewLifecycleOwner());
        chartViewModel.getChartData().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    hideLoading();
                    binding.setLoading(false);
                    setChartData(listResource.data);
                    break;
                case ERROR:
                    Toast.makeText(getBaseActivity(), "An Error Occured", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    binding.setLoading(false);
                    break;
                case LOADING:
                    showLoading("PLEASE WAIT....");
                    break;

            }
        });


        //months
        monthsShort = new ArrayList<>();
        String[] months = new DateFormatSymbols().getShortMonths();
        for (int i = 0; i < months.length - 1; i++) {
            String month = months[i];
            System.out.println("Month [" + i + "] = " + month);
            monthsShort.add(month);
        }

    }


    private void setChartData(ChartModel data) {
        if (data == null) return;


        BarDataSet confirmedSet;
        confirmedSet = new BarDataSet(data.getConfirmed(), "Confirmed");
        confirmedSet.setColor(getResources().getColor(R.color.colorAccent));
        confirmedSet.setValueTextColor(getResources().getColor(R.color.colorAccent));

        BarDataSet deathSet;
        deathSet = new BarDataSet(data.getDeaths(), "Deaths");
        deathSet.setColor(getResources().getColor(R.color.themeRed));
        deathSet.setValueTextColor(getResources().getColor(R.color.themeRed));

        BarDataSet recoveredSet;
        recoveredSet = new BarDataSet(data.getRecovered(), " Recovered");
        recoveredSet.setColor(getResources().getColor(R.color.themeOrange));
        recoveredSet.setValueTextColor(getResources().getColor(R.color.themeOrange));

        BarData barData = new BarData(confirmedSet, deathSet, recoveredSet);
        barData.setValueFormatter(new LargeValueFormatter());


        float barWidth = 0.3f;
        float groupSpace = 0.4f;
        float barSpace = 0f;

        binding.barChartCovidDataAnalysis.setData(barData);
        binding.barChartCovidDataAnalysis.getBarData().setBarWidth(barWidth);
        binding.barChartCovidDataAnalysis.getXAxis().setAxisMinimum(0);
        binding.barChartCovidDataAnalysis.getXAxis().setAxisMaximum(0 + binding.barChartCovidDataAnalysis.getBarData().getGroupWidth(groupSpace, barSpace) * monthsShort.size());
        binding.barChartCovidDataAnalysis.groupBars(0, groupSpace, barSpace);
        binding.barChartCovidDataAnalysis.getData().setHighlightEnabled(false);
        binding.barChartCovidDataAnalysis.invalidate();

        Legend l = binding.barChartCovidDataAnalysis.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setTextColor(getResources().getColor(R.color.blue));

        XAxis xAxis = binding.barChartCovidDataAnalysis.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsShort));
        xAxis.setTextColor(getResources().getColor(R.color.blue));
        xAxis.setGridColor(getResources().getColor(R.color.blue));

        binding.barChartCovidDataAnalysis.getAxisRight().setEnabled(false);
        YAxis leftAxis = binding.barChartCovidDataAnalysis.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(getResources().getColor(R.color.blue));
        leftAxis.setGridColor(getResources().getColor(R.color.blue));


    }


    private void init() {
        //toolbar
        if (requireActivity().getActionBar() != null) {
            Objects.requireNonNull(requireActivity().getActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            Objects.requireNonNull(requireActivity().getActionBar()).setCustomView(R.layout.toolbar);

        }

        //tablayout
        binding.vpRegion.setAdapter(new CustomPagerAdapter(getChildFragmentManager(), getContext(), 0));
        binding.tlMainStatistics.setCustomTabView((container, position, adapter) -> {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View tab = inflater.inflate(R.layout.layout_custom_tab, container, false);
            TextView customText = tab.findViewById(R.id.textViewCustomTab);
            switch (position) {
                case 0:
                case 1:
                    customText.setText(adapter.getPageTitle(position));
                    break;
                default:
                    throw new IllegalStateException("Invalid position: " + position);
            }
            return tab;
        });
        binding.tlMainStatistics.setViewPager(binding.vpRegion);
        binding.vpRegion.setOffscreenPageLimit(2);

        //chart
        binding.barChartCovidDataAnalysis.setDescription(null);
        binding.barChartCovidDataAnalysis.setPinchZoom(false);
        binding.barChartCovidDataAnalysis.setScaleEnabled(false);
        binding.barChartCovidDataAnalysis.setDrawBarShadow(false);
        binding.barChartCovidDataAnalysis.setDrawGridBackground(false);
    }
}

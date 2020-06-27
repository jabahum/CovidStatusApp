package com.example.covidstatusapp.fragments.statistics;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.adapters.CustomPagerAdapter;
import com.example.covidstatusapp.models.ChartModel;
import com.example.covidstatusapp.utils.FontUtils;
import com.example.covidstatusapp.viewModel.CountryChartViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Objects;

public class StatisticsFragment extends Fragment {
    private SmartTabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView pageTitle;
    private TextView chartTitle;
    private CountryChartViewModel chartViewModel;
    BarChart barChart;

    ArrayList<String> monthsShort;
    private ImageView optionsImg;
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    ImageView backImage;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        mTabLayout = view.findViewById(R.id.tl_main_statistics);
        mViewPager = view.findViewById(R.id.vp_region);
        pageTitle = view.findViewById(R.id.pagetitle);
        chartTitle = view.findViewById(R.id.chart_title);
        barChart = view.findViewById(R.id.bar_chart_covid_data_analysis);
        progressBar = view.findViewById(R.id.progress);

        optionsImg = view.findViewById(R.id.img_options);
        backImage = view.findViewById(R.id.toolbar_back_button);


        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(chartTitle);

        init();
        subscriberObservers();

        backImage.setOnClickListener(view1 -> {
        });

    }

    private void subscriberObservers() {
        //Chart data
        chartViewModel = new ViewModelProvider(this).get(CountryChartViewModel.class);
        chartViewModel.init("uganda");
        chartViewModel.observeChartData().removeObservers(getViewLifecycleOwner());
        chartViewModel.observeChartData().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {

                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    setChartData(listResource.data);
                    break;
                case ERROR:
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
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


        BarDataSet dataSet;
        dataSet = new BarDataSet(data.getConfirmed(), " Total Confirmed Cases");
        dataSet.setColor(getResources().getColor(R.color.red));
        dataSet.setValueTextColor(Color.WHITE);

        BarData barData = new BarData(dataSet);
        barData.setValueFormatter(new LargeValueFormatter());

        float barWidth = 0.3f;

        barChart.setData(barData);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getBarWidth() * monthsShort.size());
        barChart.getData().setHighlightEnabled(false);
        barChart.invalidate();

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setTextColor(getResources().getColor(R.color.blue));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsShort));
        xAxis.setTextColor(getResources().getColor(R.color.blue));
        xAxis.setGridColor(getResources().getColor(R.color.blue));

        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
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
        mViewPager.setAdapter(new CustomPagerAdapter(getChildFragmentManager(), getContext()));
        mTabLayout.setCustomTabView((container, position, adapter) -> {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View tab = inflater.inflate(R.layout.layout_custom_tab, container, false);
            TextView customText = tab.findViewById(R.id.textViewCustomTab);
            FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(customText);
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
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

        //chart
        barChart.setDescription(null);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
    }
}

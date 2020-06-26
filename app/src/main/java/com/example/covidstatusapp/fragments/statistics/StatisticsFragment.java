package com.example.covidstatusapp.fragments.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.adapters.CustomPagerAdapter;
import com.example.covidstatusapp.models.CountryChartModel;
import com.example.covidstatusapp.utils.FontUtils;
import com.example.covidstatusapp.utils.Resource;
import com.example.covidstatusapp.viewModel.CountryChartViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.List;

public class StatisticsFragment extends Fragment {
    private SmartTabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView pageTitle;
    private TextView chartTitle;
    private CountryChartViewModel chartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_fragment, container, false);    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tl_main_statistics);
        mViewPager = view.findViewById(R.id.vp_region);
        pageTitle = view.findViewById(R.id.pagetitle);
        chartTitle = view.findViewById(R.id.chart_title);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(chartTitle);
        init();
        subscriberObservers();
    }

    private void subscriberObservers() {
        chartViewModel = new ViewModelProvider(this).get(CountryChartViewModel.class);
        chartViewModel.init("uganda");
        chartViewModel.getCountryChartRepository().removeObservers(getViewLifecycleOwner());
        chartViewModel.getCountryChartRepository().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null) {
                switch (listResource.status){
                    case ERROR:
                        break;
                    case SUCCESS:
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private void init() {
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
    }
}

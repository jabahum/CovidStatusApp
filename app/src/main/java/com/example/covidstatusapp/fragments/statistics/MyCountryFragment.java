package com.example.covidstatusapp.fragments.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.FontUtils;
import com.example.covidstatusapp.dashboard.models.LiveCases;
import com.example.covidstatusapp.models.Country;
import com.example.covidstatusapp.viewModel.MyCountryViewModel;

import java.util.List;

public class MyCountryFragment extends Fragment {

    private TextView affectedCases;
    private TextView deathsCases;
    private TextView recoveredCases;
    private TextView activeCases;
    private TextView seriousCases;

    private TextView affectedCasesValue;
    private TextView deathsCasesValue;
    private TextView recoveredCasesValue;
    private TextView activeCasesValue;
    private TextView seriousCasesValue;

    private MyCountryViewModel countryViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mycountry_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        affectedCases = view.findViewById(R.id.country_txt_affected);
        deathsCases = view.findViewById(R.id.country_txt_deaths);
        recoveredCases = view.findViewById(R.id.country_txt_recovered);
        activeCases = view.findViewById(R.id.country_txt_active);
        seriousCases = view.findViewById(R.id.country_txt_serious);

        affectedCasesValue = view.findViewById(R.id.country_txt_affected_value);
        deathsCasesValue = view.findViewById(R.id.country_txt_deaths_value);
        recoveredCasesValue = view.findViewById(R.id.country_txt_recovered_value);
        activeCasesValue = view.findViewById(R.id.country_txt_active_value);
        seriousCasesValue = view.findViewById(R.id.country_txt_serious_value);

        subscribeObservers();

        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(affectedCases);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(deathsCases);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(recoveredCases);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(activeCases);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(seriousCases);

        FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(affectedCasesValue);
        FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(deathsCasesValue);
        FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(recoveredCasesValue);
        FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(activeCasesValue);
        FontUtils.getFontUtils(getActivity()).setTextViewRegularFont(seriousCasesValue);


    }


    private void subscribeObservers() {
        countryViewModel = new ViewModelProvider(this).get(MyCountryViewModel.class);
        countryViewModel.init("south-africa");
        countryViewModel.getMyCountryRepository().removeObservers(getViewLifecycleOwner());
        countryViewModel.getMyCountryRepository().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case ERROR:
                        break;
                    case SUCCESS:
                        if (listResource.data != null) {
                            //setCountryData(listResource.data);
                        }
                        break;
                    case LOADING:
                        break;
                }
            }
        });


    }

    private void setCountryData(List<LiveCases> liveCases) {
        for (LiveCases cases : liveCases){

            if (cases.getDate().equals(liveCases.get(0).getDate())){
                /*affectedCasesValue.setText(String.valueOf(cases.getConfirmed()));
                deathsCasesValue.setText(String.valueOf(cases.getDeaths()));
                recoveredCasesValue.setText(String.valueOf(cases.getRecovered()));*/
            }

        }
    }
}

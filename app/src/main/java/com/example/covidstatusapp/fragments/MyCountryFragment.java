package com.example.covidstatusapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.FontUtils;

public class MyCountryFragment extends Fragment {

    TextView affectedCases;
    TextView deathsCases;
    TextView recoveredCases;
    TextView activeCases;
    TextView seriousCases;

    TextView affectedCasesValue;
    TextView deathsCasesValue;
    TextView recoveredCasesValue;
    TextView activeCasesValue;
    TextView seriousCasesValue;


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
}

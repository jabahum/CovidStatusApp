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
import com.example.covidstatusapp.models.Global;
import com.example.covidstatusapp.models.SummaryResponse;
import com.example.covidstatusapp.viewModel.GlobalViewModel;
import com.example.covidstatusapp.viewModel.MyCountryViewModel;

public class GlobalFragment extends Fragment {

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

    private GlobalViewModel globalViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.global_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        affectedCases = view.findViewById(R.id.global_txt_affected);
        deathsCases = view.findViewById(R.id.global_txt_deaths);
        recoveredCases = view.findViewById(R.id.global_txt_recovered);
        activeCases = view.findViewById(R.id.global_txt_active);
        seriousCases = view.findViewById(R.id.global_txt_serious);

        affectedCasesValue = view.findViewById(R.id.global_txt_affected_value);
        deathsCasesValue = view.findViewById(R.id.global_txt_deaths_value);
        recoveredCasesValue = view.findViewById(R.id.global_txt_recovered_value);
        activeCasesValue = view.findViewById(R.id.global_txt_active_value);
        seriousCasesValue = view.findViewById(R.id.global_txt_serious_value);

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

        globalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
        globalViewModel.init();
        globalViewModel.getGlobalRepository().removeObservers(getViewLifecycleOwner());
        globalViewModel.getGlobalRepository().observe(getViewLifecycleOwner(), summaryResponseResource -> {
            if (summaryResponseResource != null) {
                switch (summaryResponseResource.status) {
                    case ERROR:
                        break;
                    case LOADING:
                        break;
                    case SUCCESS:
                        if (summaryResponseResource.data != null) {
                            setGlobalSummary(summaryResponseResource.data.getGlobal());
                        }
                        break;
                }
            }

        });

    }

    private void setGlobalSummary(Global summary) {
        affectedCasesValue.setText(String.valueOf(summary.getNewConfirmed()));
        deathsCasesValue.setText(String.valueOf(summary.getNewDeaths()));
        recoveredCasesValue.setText(String.valueOf(summary.getNewRecovered()));

    }
}

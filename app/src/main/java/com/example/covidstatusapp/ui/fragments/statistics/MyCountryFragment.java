package com.example.covidstatusapp.ui.fragments.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.ViewModelProviderFactory;
import com.example.covidstatusapp.databinding.MycountryFragmentBinding;
import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui._base.BaseFragment;
import com.example.covidstatusapp.ui.models.Country;
import com.example.covidstatusapp.ui.utils.CommonUtils;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;
import com.example.covidstatusapp.ui.viewModel.GlobalViewModel;

import java.util.List;

import javax.inject.Inject;

public class MyCountryFragment extends BaseFragment<MycountryFragmentBinding, GlobalViewModel> {


    MycountryFragmentBinding binding;
    GlobalViewModel globalViewModel;

    @Inject
    MainApi api;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    PreferenceManager preferenceManager;


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mycountry_fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = getViewDataBinding();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeObservers();
    }

    @Override
    public GlobalViewModel getViewModel() {
        globalViewModel = new ViewModelProvider(getBaseActivity(), providerFactory).get(GlobalViewModel.class);
        return globalViewModel;
    }


    private void subscribeObservers() {
        globalViewModel.getGlobal().removeObservers(getViewLifecycleOwner());
        globalViewModel.getGlobal().observe(getViewLifecycleOwner(), summaryResponseResource -> {
            if (summaryResponseResource != null) {
                switch (summaryResponseResource.status) {
                    case ERROR:
                        Toast.makeText(getActivity(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                    case SUCCESS:
                        if (summaryResponseResource.data != null) {
                            setMyCountrySummary(summaryResponseResource.data.getCountries());
                        }
                        break;
                }
            }

        });


    }

    private void setMyCountrySummary(List<Country> countryList) {
        if (countryList != null) {
            for (Country country : countryList) {
                if (country.getCountry().equals(preferenceManager.getSelectedCountry())) {
                    binding.countryTxtAffectedValue.setText(CommonUtils.numberSeparator(country.getTotalConfirmed()));
                    binding.countryTxtDeathsValue.setText(CommonUtils.numberSeparator(country.getTotalDeaths()));
                    binding.countryTxtRecoveredValue.setText(CommonUtils.numberSeparator(country.getTotalRecovered()));
                    binding.countryTxtActiveValue.setText(CommonUtils.numberSeparator(country.getNewConfirmed()));
                }

            }

        }
    }


}

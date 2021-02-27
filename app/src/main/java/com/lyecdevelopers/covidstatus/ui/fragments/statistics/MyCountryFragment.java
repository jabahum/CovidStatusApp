package com.lyecdevelopers.covidstatus.ui.fragments.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.lyecdevelopers.covidstatus.ViewModelProviderFactory;
import com.example.covidstatusapp.databinding.MycountryFragmentBinding;
import com.lyecdevelopers.covidstatus.network.MainApi;
import com.lyecdevelopers.covidstatus.ui._base.BaseFragment;
import com.lyecdevelopers.covidstatus.ui.models.Country;
import com.lyecdevelopers.covidstatus.ui.utils.CommonUtils;
import com.lyecdevelopers.covidstatus.ui.utils.sharedPreferences.PreferenceManager;
import com.lyecdevelopers.covidstatus.ui.viewModel.GlobalViewModel;

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
                        hideLoading();
                        binding.setLoading(false);
                        Toast.makeText(getActivity(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        binding.setLoading(true);
                        showLoading("Loading...");
                        break;
                    case SUCCESS:
                        if (summaryResponseResource.data != null) {
                            setMyCountrySummary(summaryResponseResource.data.getCountries());
                            hideLoading();
                            binding.setLoading(false);
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

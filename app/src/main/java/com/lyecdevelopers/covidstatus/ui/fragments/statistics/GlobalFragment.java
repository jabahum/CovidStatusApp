package com.lyecdevelopers.covidstatus.ui.fragments.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.lyecdevelopers.covidstatus.ViewModelProviderFactory;
import com.lyecdevelopers.covidstatus.network.MainApi;

import com.lyecdevelopers.covidstatus.ui._base.BaseFragment;
import com.lyecdevelopers.covidstatus.ui.models.Global;
import com.lyecdevelopers.covidstatus.ui.utils.CommonUtils;
import com.lyecdevelopers.covidstatus.ui.utils.sharedPreferences.PreferenceManager;
import com.lyecdevelopers.covidstatus.ui.viewModel.GlobalViewModel;
import com.lyecdevelopers.covidstatusapp.R;
import com.lyecdevelopers.covidstatusapp.databinding.GlobalFragmentBinding;

import javax.inject.Inject;


public class GlobalFragment extends BaseFragment<GlobalFragmentBinding, GlobalViewModel> {

    GlobalFragmentBinding binding;
    private GlobalViewModel globalViewModel;

    @Inject
    MainApi api;

    @Inject
    PreferenceManager preferenceManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.global_fragment;
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
                        hideLoading();
                        showLoading("Loading..");
                        break;
                    case SUCCESS:
                        if (summaryResponseResource.data != null) {
                            setGlobalSummary(summaryResponseResource.data.getGlobal());
                            hideLoading();
                            binding.setLoading(false);
                        }
                        break;
                }
            }

        });

    }

    private void setGlobalSummary(Global globalSummary) {
        if (globalSummary !=null) {
            binding.globalTxtAffectedValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalConfirmed()));
            binding.globalTxtDeathsValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalDeaths()));
            binding.globalTxtRecoveredValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalRecovered()));
            binding.globalTxtActiveValue.setText(CommonUtils.numberSeparator(globalSummary.getNewConfirmed()));
        }
    }


}

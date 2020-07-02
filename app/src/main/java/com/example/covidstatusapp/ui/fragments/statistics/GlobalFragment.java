package com.example.covidstatusapp.ui.fragments.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.ViewModelProviderFactory;
import com.example.covidstatusapp.databinding.GlobalFragmentBinding;
import com.example.covidstatusapp.network.MainApi;
import com.example.covidstatusapp.ui._base.BaseFragment;
import com.example.covidstatusapp.ui.models.Global;
import com.example.covidstatusapp.ui.utils.CommonUtils;
import com.example.covidstatusapp.ui.utils.sharedPreferences.PreferenceManager;
import com.example.covidstatusapp.ui.viewModel.GlobalViewModel;

import javax.inject.Inject;

import static com.example.covidstatusapp.ui.utils.Resource.Status.ERROR;
import static com.example.covidstatusapp.ui.utils.Resource.Status.LOADING;
import static com.example.covidstatusapp.ui.utils.Resource.Status.SUCCESS;


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
                        Toast.makeText(getActivity(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
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

    private void setGlobalSummary(Global globalSummary) {
        if (globalSummary !=null) {
            binding.globalTxtAffectedValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalConfirmed()));
            binding.globalTxtDeathsValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalDeaths()));
            binding.globalTxtRecoveredValue.setText(CommonUtils.numberSeparator(globalSummary.getTotalRecovered()));
            binding.globalTxtActiveValue.setText(CommonUtils.numberSeparator(globalSummary.getNewConfirmed()));
        }
    }


}

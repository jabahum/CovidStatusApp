package com.example.covidstatusapp.di.modules.main;

import androidx.lifecycle.ViewModel;

import com.example.covidstatusapp.di.ViewModelKey;
import com.example.covidstatusapp.ui.viewModel.CountryChartViewModel;
import com.example.covidstatusapp.ui.viewModel.GlobalViewModel;
import com.example.covidstatusapp.ui.viewModel.MyCountryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(GlobalViewModel.class)
    public abstract ViewModel bindGlobalViewModel(GlobalViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyCountryViewModel.class)
    public abstract ViewModel bindMyCountryViewModel(MyCountryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CountryChartViewModel.class)
    public abstract ViewModel bindCountryChartViewModel(CountryChartViewModel viewModel);
}

package com.lyecdevelopers.covidstatus.di;

import androidx.lifecycle.ViewModelProvider;

import com.lyecdevelopers.covidstatus.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}

package com.example.covidstatusapp.di.modules.main;

import com.example.covidstatusapp.ui.fragments.AboutFragment;
import com.example.covidstatusapp.ui.fragments.home.HomeFragment;
import com.example.covidstatusapp.ui.fragments.statistics.GlobalFragment;
import com.example.covidstatusapp.ui.fragments.statistics.MyCountryFragment;
import com.example.covidstatusapp.ui.fragments.statistics.StatisticsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract StatisticsFragment contributeStatisticsFragment();

    @ContributesAndroidInjector
    abstract GlobalFragment contributeGlobalFragment();

    @ContributesAndroidInjector
    abstract MyCountryFragment contributeMyCountryFragment();

    @ContributesAndroidInjector
    abstract AboutFragment contributeAboutFragment();


}

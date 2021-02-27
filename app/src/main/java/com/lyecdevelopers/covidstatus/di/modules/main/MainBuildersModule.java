package com.lyecdevelopers.covidstatus.di.modules.main;

import com.lyecdevelopers.covidstatus.ui.fragments.AboutFragment;
import com.lyecdevelopers.covidstatus.ui.fragments.home.HomeFragment;
import com.lyecdevelopers.covidstatus.ui.fragments.statistics.GlobalFragment;
import com.lyecdevelopers.covidstatus.ui.fragments.statistics.MyCountryFragment;
import com.lyecdevelopers.covidstatus.ui.fragments.statistics.StatisticsFragment;

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

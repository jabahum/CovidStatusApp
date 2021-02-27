package com.lyecdevelopers.covidstatus.di;

import com.lyecdevelopers.covidstatus.di.modules.main.MainBuildersModule;
import com.lyecdevelopers.covidstatus.di.modules.main.MainModule;
import com.lyecdevelopers.covidstatus.di.modules.main.MainScope;
import com.lyecdevelopers.covidstatus.di.modules.main.MainViewModelsModule;
import com.lyecdevelopers.covidstatus.ui.activities.DashBoardActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainBuildersModule.class,
            MainModule.class,
            MainViewModelsModule.class,
    })
    abstract DashBoardActivity contributeDashBoardActivity();
}

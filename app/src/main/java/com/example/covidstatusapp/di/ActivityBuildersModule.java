package com.example.covidstatusapp.di;

import com.example.covidstatusapp.di.modules.AppModule;
import com.example.covidstatusapp.di.modules.main.MainBuildersModule;
import com.example.covidstatusapp.di.modules.main.MainModule;
import com.example.covidstatusapp.di.modules.main.MainScope;
import com.example.covidstatusapp.di.modules.main.MainViewModelsModule;
import com.example.covidstatusapp.ui.activities.DashBoardActivity;

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

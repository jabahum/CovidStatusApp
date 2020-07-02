package com.example.covidstatusapp.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.databinding.ActivityDashboardBinding;
import com.example.covidstatusapp.ui._base.BaseActivity;

public class DashBoardActivity extends BaseActivity<ActivityDashboardBinding> {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    NavHostFragment navHostFragment;
    ActivityDashboardBinding binding;


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setUpNavigation();
    }


    private void setUpNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(binding.navigation, navHostFragment.getNavController());
        }
    }


}

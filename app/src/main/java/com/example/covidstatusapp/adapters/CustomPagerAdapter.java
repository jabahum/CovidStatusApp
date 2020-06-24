package com.example.covidstatusapp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.fragments.GlobalFragment;
import com.example.covidstatusapp.fragments.MyCountryFragment;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    public CustomPagerAdapter(@NonNull FragmentManager fm ,Context context) {
        super(fm);
        this.context= context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyCountryFragment();
            case 1:
                return new GlobalFragment();

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getStringArray(R.array.statistics_tabs_array)[position];
    }
}

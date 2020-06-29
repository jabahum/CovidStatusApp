package com.example.covidstatusapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager implements AppPreferenceManager  {

    private static final String SELECTED_COUNTRY = "SelectedCountry";




    private final SharedPreferences mPrefs;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        editor = mPrefs.edit();
    }

    @Override
    public void setSelectedCountry(String selectedCountry) {
        editor.putString(SELECTED_COUNTRY, selectedCountry);
        editor.apply();
    }

    @Override
    public String getSelectedCountry() {
        return mPrefs.getString(SELECTED_COUNTRY, "[]");
    }
}

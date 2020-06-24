package com.example.covidstatusapp.dashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.viewModels.CountriesViewModel;
import com.example.covidstatusapp.fragments.AboutFragment;
import com.example.covidstatusapp.fragments.HomeFragment;
import com.example.covidstatusapp.fragments.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    Spinner spinner;
    TextView txtCountry;
    Toolbar toolbar;

    ArrayAdapter<String> countriesArrayAdapter;
    ArrayList<String> countriesList;
    String countrySelected;

    CountriesViewModel countriesViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);

        loadFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);



    }

    private void setSelectedCountry() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countrySelected = adapterView.getItemAtPosition(i).toString();
                txtCountry.setText(countrySelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCountriesData() {
        countriesViewModel = new ViewModelProvider(DashBoardActivity.this).get(CountriesViewModel.class);
        countriesViewModel.init();
        countriesViewModel.getCountriesRepository().observe(
                this,
                new Observer<List<Countries>>() {
                    @Override
                    public void onChanged(List<Countries> countriesResponse) {
                        Timber.d(TAG,countriesResponse);
                        countriesList = new ArrayList<>();

                        if (countriesResponse !=null){
                            for (Countries countries : countriesResponse){
                                countriesList.add(countries.getCountry());
                            }
                        }
                        countriesArrayAdapter = new ArrayAdapter<String>(DashBoardActivity.this,
                                android.R.layout.simple_spinner_item, countriesList);
                        spinner.setAdapter(countriesArrayAdapter);

                    }
                });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                fragment = new HomeFragment();
                break;
            case R.id.action_statistics:
                fragment = new StatisticsFragment();
                break;
            case R.id.action_help:
                fragment = new AboutFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}

package com.example.covidstatusapp.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.countrydetails.DetailsActivity;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.viewModels.CountriesViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DashBoardActivity extends AppCompatActivity {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    GridLayout mainGrid;
    Spinner spinner;
    CardView worldCardView;
    CardView africaCardView;
    CardView eastAfricaCardView;
    CardView countryCardView;
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

        spinner = findViewById(R.id.sp_countries);
        mainGrid = findViewById(R.id.mainGrid);
        worldCardView = findViewById(R.id.world_stat);
        africaCardView = findViewById(R.id.africa_stat);
        eastAfricaCardView = findViewById(R.id.east_africa_stat);
        countryCardView = findViewById(R.id.country_stat);
        txtCountry = findViewById(R.id.txt_country);
        toolbar = findViewById(R.id.toolbar);

        initToolbar(toolbar,true);

        countryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsIntent = new Intent(DashBoardActivity.this, DetailsActivity.class);
                detailsIntent.putExtra("SelectedCountry",countrySelected);
                startActivity(detailsIntent);
            }
        });

        setSelectedCountry();

        getCountriesData();


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

    private void initToolbar(final Toolbar toolbar, boolean showTitle) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(showNavigationButton);
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }
}

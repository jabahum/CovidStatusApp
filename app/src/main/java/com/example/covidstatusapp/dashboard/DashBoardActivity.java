package com.example.covidstatusapp.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.viewModels.ConfirmedCasesViewModel;
import com.example.covidstatusapp.dashboard.viewModels.CountriesViewModel;
import com.example.covidstatusapp.dashboard.viewModels.LiveCasesViewModel;


import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DashBoardActivity extends AppCompatActivity {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    GridLayout mainGrid;
    Spinner spinner;

    ArrayAdapter<String> countriesArrayAdapter;
    ArrayList<String> countriesList;

    CountriesViewModel countriesViewModel;
    LiveCasesViewModel liveCasesViewModel;
    ConfirmedCasesViewModel confirmedCasesViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        spinner = findViewById(R.id.sp_countries);
        mainGrid = findViewById(R.id.mainGrid);

        getCountriesData();


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

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(DashBoardActivity.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(DashBoardActivity.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*Intent intent = new Intent(DashBoardActivity.this,ActivityOne.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);*/

                }
            });
        }
    }

}

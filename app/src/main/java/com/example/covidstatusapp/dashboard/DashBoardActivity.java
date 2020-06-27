package com.example.covidstatusapp.dashboard;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.covidstatusapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    public static final String TAG = DashBoardActivity.class.getSimpleName();


    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    ArrayAdapter<String> countriesArrayAdapter;
    ArrayList<String> countriesList;
    String countrySelected;

    NavHostFragment navHostFragment;

    //CountriesViewModel countriesViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setUpNavigation();
        //initToolBar();

    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder()
                    .setFallbackOnNavigateUpListener(() -> {
                        super.onBackPressed();

                        return true;
                    })
                    .build();

            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
            navController.addOnDestinationChangedListener((NavController controller, NavDestination destination, Bundle arguments) -> {

            });

        }
    }

    private void setUpNavigation() {
        bottomNavigationView = findViewById(R.id.navigation);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        }
    }


}

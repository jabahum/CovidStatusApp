package com.example.covidstatusapp.dashboard;

import android.content.Intent;
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

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.APIClient;
import com.example.covidstatusapp.common.APIinterface;
import com.example.covidstatusapp.dashboard.models.ConfirmedCases;
import com.example.covidstatusapp.dashboard.models.Countries;
import com.example.covidstatusapp.dashboard.models.LiveCases;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DashBoardActivity extends AppCompatActivity {

    public static final String TAG = DashBoardActivity.class.getSimpleName();
    APIinterface apIinterface;
    List<Countries> mCountries;
    Spinner spinner;
    ArrayAdapter<String> countriesArrayAdapter;
    ArrayList<String> countriesList;
    GridLayout mainGrid;

    public List<Countries> getmCountries() {
        return mCountries;
    }

    public void setmCountries(List<Countries> mCountries) {
        this.mCountries = mCountries;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        spinner = findViewById(R.id.sp_countries);
        mainGrid = findViewById(R.id.mainGrid);

        mCountries = new ArrayList<>();
        countriesList = new ArrayList<>();
        apIinterface = APIClient.getClient().create(APIinterface.class);

        getConfirmedCases();
        getCountries();
        getLiveCases();

        setSingleEvent(mainGrid);
        setToggleEvent(mainGrid);

        countriesArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countriesList);
        spinner.setAdapter(countriesArrayAdapter);

    }

    private void getConfirmedCases() {
        apIinterface.getConfirmedCase().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ConfirmedCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ConfirmedCases> confirmedCases) {

                        Timber.i(TAG, confirmedCases.toString());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getLiveCases() {
        apIinterface.getLiveCases().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LiveCases>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LiveCases> liveCases) {

                        Timber.i(TAG, liveCases.toString());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getCountries() {
        apIinterface.getCountries().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Countries>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Countries> countries) {

                        Timber.i(TAG, countries.toString());
                        for (Countries country : countries){
                            countriesList.add(country.getCountry());
                        }
                        setmCountries(countries);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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

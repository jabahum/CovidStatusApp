package com.example.covidstatusapp.countrydetails;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.CommonUtils;
import com.example.covidstatusapp.countrydetails.adapter.CountryAllStatusAdapter;
import com.example.covidstatusapp.countrydetails.models.CountryAllStatus;
import com.example.covidstatusapp.countrydetails.viewModel.CountryAllStatusViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = DetailsActivity.class.getSimpleName();

    Button btnFrom;
    Button btnTo;
    RecyclerView mRecyclerView;


    CountryAllStatusViewModel countryAllStatusViewModel;
    CountryAllStatusAdapter countryAllStatusAdapter;

    private int mYear, mMonth, mDay;
    private String from, to;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnFrom = findViewById(R.id.btn_from);
        btnTo = findViewById(R.id.btn_to);
        mRecyclerView = findViewById(R.id.recycler_view_details);

        from = btnFrom.getText().toString();
        to = btnTo.getText().toString();

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(btnFrom);
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(btnTo);
            }
        });

        Intent intent = getIntent();
        String selectedCountry = intent.getStringExtra("SelectedCountry");

        setRecyclerData(selectedCountry);

    }

    private void setRecyclerData(String selectedCountry) {
        countryAllStatusViewModel = new ViewModelProvider(DetailsActivity.this).get(CountryAllStatusViewModel.class);
        countryAllStatusViewModel.init(selectedCountry, from, to);
        countryAllStatusViewModel.getCountryAllStatusRepository()
                .observe(this, new Observer<List<CountryAllStatus>>() {
                    @Override
                    public void onChanged(List<CountryAllStatus> countryAllStatuses) {

                        if (countryAllStatuses != null){
                            countryAllStatusAdapter =  new CountryAllStatusAdapter(DetailsActivity.this,countryAllStatuses);
                            mRecyclerView.setAdapter(countryAllStatusAdapter);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, RecyclerView.VERTICAL,false));
                        }

                    }
                });
    }

    private void setDate(final Button btn) {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btn.setText(String.format(Locale.US, "%d-%s-%s", year, CommonUtils.zero(month + 1), CommonUtils.zero(dayOfMonth)));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

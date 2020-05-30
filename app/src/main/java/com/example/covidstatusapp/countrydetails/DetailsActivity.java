package com.example.covidstatusapp.countrydetails;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatusapp.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = DetailsActivity.class.getSimpleName();

    Button btnFrom;
    Button btnTo;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnFrom = findViewById(R.id.btn_from);
        btnTo = findViewById(R.id.btn_to);
        mRecyclerView = findViewById(R.id.recycler_view_details);



    }
}

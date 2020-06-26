package com.example.covidstatusapp.countrydetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.countrydetails.models.CountryAllStatus;

import java.util.ArrayList;
import java.util.List;

public class CountryAllStatusAdapter extends RecyclerView.Adapter<CountryAllStatusAdapter.ItemViewHolder> {

    private List<CountryAllStatus> dataList = new ArrayList<>();

    @NonNull
    @Override
    public CountryAllStatusAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CountryAllStatusAdapter.ItemViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(dataList.get(position));

    }


    public void setDataList(List<CountryAllStatus> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView fullCountryName,shortCountryName,date,activeCases,confirmedCases,deathCases,recoveredCases;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            fullCountryName = itemView.findViewById(R.id.txt_full_country_name);
            shortCountryName = itemView.findViewById(R.id.txt_country_short);
            date = itemView.findViewById(R.id.txt_date);
            activeCases = activeCases;
            confirmedCases = itemView.findViewById(R.id.txt_confirmed_cases_numbers);
            deathCases = itemView.findViewById(R.id.txt_deaths_cases_numbers);
            recoveredCases = itemView.findViewById(R.id.txt_recovered_cases_numbers);
        }

        public void bind(CountryAllStatus countryAllStatus) {
            /*fullCountryName.setText(countryAllStatus.getCountry());
            shortCountryName.setText(countryAllStatus.getCountryCode());
            date.setText(countryAllStatus.getDate());
            confirmedCases.setText(countryAllStatus.getConfirmed());
            deathCases.setText(countryAllStatus.getDeaths());
            recoveredCases.setText(countryAllStatus.getRecovered());*/

        }
    }
}

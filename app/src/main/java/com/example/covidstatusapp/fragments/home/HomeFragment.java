package com.example.covidstatusapp.fragments.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.models.Country;
import com.example.covidstatusapp.models.SummaryResponse;
import com.example.covidstatusapp.utils.CommonUtils;
import com.example.covidstatusapp.utils.Config;
import com.example.covidstatusapp.utils.FontUtils;
import com.example.covidstatusapp.utils.PreferenceManager;
import com.example.covidstatusapp.viewModel.GlobalViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView pageTitle;
    private TextView pageSubTitle;
    private TextView pageParagraph;
    private Button callbutton;
    private Button smsButton;
    private TextView pageSubHeadingTitle, pageSubHeadingTitle2, pageSubHeadingTitle3;
    private TextView cautionOne;
    private TextView cautionTwo;
    private TextView cautionThree;

    TextView mConfirmed, mDeath, mRecovered;
    TextView countryConfirmed, countryDeath, countryRecovered;
    private GlobalViewModel globalViewModel;

    NavController navController;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    PieChart mGlobalDataPieChart;
    PieChart mCountryDataPieChart;
    ProgressBar progressBar;
    CountryCodePicker codePicker;
    String SELECTED_COUNTRY;
    PreferenceManager preferenceManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        pageTitle = view.findViewById(R.id.pagetitle);
        pageSubTitle = view.findViewById(R.id.pageSubTitle);
        pageParagraph = view.findViewById(R.id.pageParagraph);
        pageSubHeadingTitle = view.findViewById(R.id.prevention_title);
        pageSubHeadingTitle2 = view.findViewById(R.id.global_cases_title);
        pageSubHeadingTitle3 = view.findViewById(R.id.country_cases_title);
        mGlobalDataPieChart = view.findViewById(R.id.home_global_data_pie_chart);
        mCountryDataPieChart = view.findViewById(R.id.home_country_data_pie_chart);
        codePicker = view.findViewById(R.id.sp_countries);

        mConfirmed = view.findViewById(R.id.home_confirmed);
        mDeath = view.findViewById(R.id.home_death);
        mRecovered = view.findViewById(R.id.home_recovered);

        countryConfirmed = view.findViewById(R.id.home_country_confirmed);
        countryDeath = view.findViewById(R.id.home_country_death);
        countryRecovered = view.findViewById(R.id.home_country_recovered);

        callbutton = view.findViewById(R.id.btnCallButton);
        smsButton = view.findViewById(R.id.btnSmsButton);

        cautionOne = view.findViewById(R.id.txt_caution_1);
        cautionTwo = view.findViewById(R.id.txt_caution_2);
        cautionThree = view.findViewById(R.id.txt_caution_3);
        progressBar = view.findViewById(R.id.progressBar);

        preferenceManager = new PreferenceManager(requireContext(), Config.prefName);



        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        init();
        subscribeObservers();


    }


    private void subscribeObservers() {

        globalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
        globalViewModel.init();
        globalViewModel.getGlobalRepository().removeObservers(getViewLifecycleOwner());
        globalViewModel.getGlobalRepository().observe(getViewLifecycleOwner(), summaryResponseResource -> {
            if (summaryResponseResource != null) {
                switch (summaryResponseResource.status) {
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        if (summaryResponseResource.data != null) {
                            setDataGlobalDataPieChart(summaryResponseResource.data);
                            setDataCountryDataPieChart(summaryResponseResource.data.getCountries());
                            progressBar.setVisibility(View.GONE);
                        }
                        break;
                }
            }

        });

    }

    private void init() {

        //countryCode
        codePicker.setOnCountryChangeListener(() -> {
            String countryName  = codePicker.getSelectedCountryName();
            SELECTED_COUNTRY = countryName;
            preferenceManager.setSelectedCountry(SELECTED_COUNTRY);


        });

        //smsButton
        smsButton.setOnClickListener(view -> {
            Uri uri = Uri.parse("smsto:" + "+256800100066");

            Intent sms = new Intent(Intent.ACTION_SENDTO, uri);

            startActivity(sms);
        });

        //callButton
        callbutton.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else if (!CommonUtils.isSimCardAvailable(requireContext())) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            } else {
                String dial = "tel:" + "+256800100066";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        });

        //Global Chart
        mGlobalDataPieChart.getDescription().setEnabled(false);
        mGlobalDataPieChart.setDragDecelerationFrictionCoef(0.95f);
//        mGlobalDataPieChart.setDrawHoleEnabled(true);

        mGlobalDataPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mGlobalDataPieChart.setRotationEnabled(true);
        mGlobalDataPieChart.setHighlightPerTapEnabled(true);

        mGlobalDataPieChart.animateY(1500, Easing.EaseInOutQuad);
        mGlobalDataPieChart.getLegend().setEnabled(false);
        // entry label styling


        //Country Chart

        mCountryDataPieChart.getDescription().setEnabled(false);
        mCountryDataPieChart.setDragDecelerationFrictionCoef(0.95f);
//        mGlobalDataPieChart.setDrawHoleEnabled(true);

        mCountryDataPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mCountryDataPieChart.setRotationEnabled(true);
        mCountryDataPieChart.setHighlightPerTapEnabled(true);

        mCountryDataPieChart.animateY(1500, Easing.EaseInOutQuad);
        mCountryDataPieChart.getLegend().setEnabled(false);
        // entry label styling

        //Set Fonts

        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(pageParagraph);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubHeadingTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubHeadingTitle2);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubHeadingTitle3);

        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionOne);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionTwo);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionThree);

        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(callbutton);
        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(smsButton);
    }




    @SuppressLint("ResourceType")
    private void setDataGlobalDataPieChart(SummaryResponse summaryResponse) {

        int confirmed = summaryResponse.getGlobal().getTotalConfirmed();
        int death = summaryResponse.getGlobal().getTotalDeaths();
        int recovered = summaryResponse.getGlobal().getTotalRecovered();

        mConfirmed.setText(CommonUtils.numberSeparator(confirmed));
        mDeath.setText(CommonUtils.numberSeparator(death));
        mRecovered.setText(CommonUtils.numberSeparator(recovered));

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(confirmed, 1));
        entries.add(new PieEntry(death, 2));
        entries.add(new PieEntry(recovered, 3));

        PieDataSet dataSet = new PieDataSet(entries, "Global Data");

        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor(getResources().getString(R.color.colorAccent)));
        colors.add(Color.parseColor(getResources().getString(R.color.themeRed)));
        colors.add(Color.parseColor(getResources().getString(R.color.themeOrange)));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        mGlobalDataPieChart.setData(data);

        // undo all highlights
        mGlobalDataPieChart.highlightValues(null);
        mGlobalDataPieChart.setCenterText("Update:\n" + summaryResponse.getDate()
                .replace("T","\n")
                .replaceAll("Z",""));
        mGlobalDataPieChart.invalidate();

    }
    @SuppressLint("ResourceType")
    private void setDataCountryDataPieChart(List<Country> countryList) {
        if (countryList != null) {
            for (Country country : countryList) {
                if (country.getCountry().equals(preferenceManager.getSelectedCountry())) {


                    int confirmed = country.getTotalConfirmed();
                    int death = country.getTotalDeaths();
                    int recovered = country.getTotalRecovered();

                    countryConfirmed.setText(CommonUtils.numberSeparator(confirmed));
                    countryDeath.setText(CommonUtils.numberSeparator(death));
                    countryRecovered.setText(CommonUtils.numberSeparator(recovered));


                    ArrayList<PieEntry> entries = new ArrayList<>();

                    entries.add(new PieEntry(confirmed, 1));
                    entries.add(new PieEntry(death, 2));
                    entries.add(new PieEntry(recovered, 3));

                    PieDataSet dataSet = new PieDataSet(entries, "Country Data");

                    dataSet.setDrawIcons(false);
                    dataSet.setDrawValues(false);
                    dataSet.setSliceSpace(3f);
                    dataSet.setIconsOffset(new MPPointF(0, 40));
                    dataSet.setSelectionShift(5f);

                    // add a lot of colors

                    ArrayList<Integer> colors = new ArrayList<>();

                    colors.add(Color.parseColor(getResources().getString(R.color.colorAccent)));
                    colors.add(Color.parseColor(getResources().getString(R.color.themeRed)));
                    colors.add(Color.parseColor(getResources().getString(R.color.themeOrange)));

                    dataSet.setColors(colors);

                    PieData data = new PieData(dataSet);
                    mCountryDataPieChart.setData(data);


                    // undo all highlights
                    mCountryDataPieChart.highlightValues(null);
                    mCountryDataPieChart.setCenterText("Update:\n" + country.getDate()
                            .replace("T","\n")
                            .replace("Z",""));

                    mCountryDataPieChart.invalidate();

                }

            }

        }
    }


}

package com.example.covidstatusapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.common.FontUtils;

public class HomeFragment extends Fragment {

    private TextView pageTitle;
    private TextView pageSubTitle;
    private TextView pageParagraph;
    private Button callbutton;
    private Button smsButton;
    private TextView pageSubHeadingTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageTitle = view.findViewById(R.id.pagetitle);
        pageSubTitle = view.findViewById(R.id.pageSubTitle);
        pageParagraph = view.findViewById(R.id.pageParagraph);
        callbutton = view.findViewById(R.id.btnCallButton);
        smsButton = view.findViewById(R.id.btnSmsButton);
        pageSubHeadingTitle = view.findViewById(R.id.prevention_title);


        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(pageParagraph);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubHeadingTitle);

        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(callbutton);
        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(smsButton);


    }
}

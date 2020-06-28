package com.example.covidstatusapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.utils.FontUtils;

public class AboutFragment extends Fragment {

    private TextView pageTitle;
    private TextView pageSubTitle;
    private TextView pageSubTitleHeading1;
    private TextView pageSubTitleHeading2;
    private TextView pageSubTitleHeading3;
    private TextView pageSubTitleHeading4;

    private TextView versionValue;
    private TextView versionLastUpdate;
    private ImageView optionsImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageTitle = view.findViewById(R.id.pagetitle);
        pageSubTitle = view.findViewById(R.id.pageSubTitle);
        pageSubTitleHeading1 = view.findViewById(R.id.pageSubTitleHeading1);
        pageSubTitleHeading2 = view.findViewById(R.id.pageSubTitleHeading2);
        pageSubTitleHeading3 =view.findViewById(R.id.pageSubTitleHeading3);
        pageSubTitleHeading4 = view.findViewById(R.id.pageSubTitleHeading4);

        optionsImg = view.findViewById(R.id.img_options);

        versionValue = view.findViewById(R.id.version_value);
        versionLastUpdate = view.findViewById(R.id.last_update);
        setFonts();
        about();
    }

    private void setFonts() {
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitle);

        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitleHeading1);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitleHeading2);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitleHeading3);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitleHeading4);

        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(versionValue);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(versionLastUpdate);

    }

    private void about() {
        //optionsImg.setOnClickListener(view -> requireActivity().finish());
    }

}

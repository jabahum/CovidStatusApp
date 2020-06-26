package com.example.covidstatusapp.fragments.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.utils.FontUtils;

public class HomeFragment extends Fragment {

    private TextView pageTitle;
    private TextView pageSubTitle;
    private TextView pageParagraph;
    private Button callbutton;
    private Button smsButton;
    private TextView pageSubHeadingTitle;
    private TextView cautionOne;
    private TextView cautionTwo;
    private TextView cautionThree;
    private ImageView optionsImg;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

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
        pageSubHeadingTitle = view.findViewById(R.id.prevention_title);


        callbutton = view.findViewById(R.id.btnCallButton);
        smsButton = view.findViewById(R.id.btnSmsButton);

        optionsImg = view.findViewById(R.id.img_options);

        cautionOne = view.findViewById(R.id.txt_caution_1);
        cautionTwo = view.findViewById(R.id.txt_caution_2);
        cautionThree = view.findViewById(R.id.txt_caution_3);

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        setFonts();
        about();
        call();
        sendSms();


    }

    private void about() {
        optionsImg.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.aboutFragment));
    }

    private void sendSms() {
        smsButton.setOnClickListener(view -> {
            Uri uri = Uri.parse("smsto:" + "+256800100066");

            Intent sms = new Intent(Intent.ACTION_SENDTO, uri);

            startActivity(sms);
        });
    }

    private void call() {
        callbutton.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else if (!isSimCardAvailable(requireContext())) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            } else {
                String dial = "tel:" + "+256800100066";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        });
    }

    private void setFonts() {
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubTitle);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(pageParagraph);
        FontUtils.getFontUtils(getActivity()).setTextViewBoldFont(pageSubHeadingTitle);

        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionOne);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionTwo);
        FontUtils.getFontUtils(getActivity()).setTextViewLightFont(cautionThree);

        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(callbutton);
        FontUtils.getFontUtils(getActivity()).setButtonBoldFont(smsButton);
    }

    public static boolean isSimCardAvailable(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }
}

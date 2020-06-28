package com.example.covidstatusapp.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

public class CommonUtils {
    public CommonUtils() {
    }
    public static String zero(int number) {
        if (number < 10) {
            return String.format(Locale.US, "0%d", number);
        } else {
            return String.valueOf(number);
        }
    }


    public static boolean isSimCardAvailable(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }
}

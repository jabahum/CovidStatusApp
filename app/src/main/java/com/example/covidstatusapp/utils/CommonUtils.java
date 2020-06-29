package com.example.covidstatusapp.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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


    public static int month (String date) {
        Date dateString =  toDate(date,"yyyy-mm-dd"); //new Date(date);
        return dateString.getMonth();
    }
    public static Date toDate(String date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.example.covidstatusapp.common;

import java.util.Locale;

public final class CommonUtils {
    public CommonUtils() {
    }
    public static String zero(int number) {
        if (number < 10) {
            return String.format(Locale.US, "0%d", number);
        } else {
            return String.valueOf(number);
        }
    }

    public static String dateFormat(String format){
        return format;
    }
}

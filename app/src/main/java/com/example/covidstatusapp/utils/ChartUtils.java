package com.example.covidstatusapp.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class ChartUtils {

    public static String numberSeparator(int value) {
        return String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(value));
    }



}

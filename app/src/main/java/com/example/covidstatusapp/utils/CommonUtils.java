package com.example.covidstatusapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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


    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target))
                return i;

        return -1;
    }

    public static String bitMapToBase64(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            bmp.recycle();
            byte[] byteArray = bao.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }


    public static String dateToString(Date date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap stringToImage(String base64) {
        try {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getCurrentDate() {
        return (GregorianCalendar.getInstance()).getTime();
    }

    public static void showKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static String commas(Double number) {
        if (number == null) return "0";
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

    public static String commas(Integer number) {
        if (number == null) return "0";
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

   /* public static String zonedDateTimeToString(ZonedDateTime dateTime) {
        if (dateTime == null) return "";
        return String.format(Locale.US, "%d %s %d", dateTime.getDayOfMonth(), dateTime.getMonth().toString(), dateTime.getYear());
    }*/

    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return ((int) dp);
    }

    public static String numberToNth(Integer n) {
        if (n == null || n <= 0) return "";

        if (n >= 11 && n <= 13) {
            return n + "ᵗʰ";
        }

        switch (n % 10) {
            case 1:
                return n + "ˢᵗ";
            case 2:
                return n + "ⁿᵈ";
            case 3:
                return n + "ʳᵈ";
            default:
                return n + "ᵗʰ";
        }
    }


    public static List<String> getLastYears(int number) {
        List<String> years = new ArrayList<>();

        Calendar prevYear;

        for (int i = 0; i <= number; i++) {
            prevYear = Calendar.getInstance();
            prevYear.add(Calendar.YEAR, -1 * i);
            years.add(prevYear.get(Calendar.YEAR) + "");
        }

        return years;
    }

    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMMM", Locale.US).format(cal.getTime());
    }

    public static String capitalize(String text) {
        if (text != null && !text.isEmpty()) {
            text = text.toLowerCase();
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }

        return "";
    }

    public static String splitText(String text) {
        String[] strings = text.split(" ");
        if (strings.length > 1) {
            StringBuilder finalString = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                if (i != strings.length - 1) {
                    finalString.append(strings[i]).append("\n");
                } else {
                    finalString.append(strings[i]);
                }
            }
            return finalString.toString();
        } else {
            return text;
        }
    }

    public static void shareApp(Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Let's farm with EzyAgric!, it's a fast and simple app we can use for our Agricultural needs.Get it at https://play.google.com/store/apps/details?id=com.ezyagric.extension.android");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }
}

package com.example.covidstatusapp.ui.utils.BindingUtils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;


public final class BindingUtils {

    @BindingAdapter({"content"})
    public static void setContent(TextView tv, String value) {
        if (value != null) {
            tv.setText(value);
        }
    }

    @BindingAdapter({"content"})
    public static void textString(TextView tv, String value) {
        if (value != null) {
            tv.setText(value);
        }
    }
}

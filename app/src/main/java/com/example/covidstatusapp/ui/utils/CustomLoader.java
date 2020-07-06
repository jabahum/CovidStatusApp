package com.example.covidstatusapp.ui.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.databinding.ProgressBarBinding;

public final class CustomLoader extends DialogFragment {
    private String message;
    ProgressBarBinding binding;

    public CustomLoader(String message){

        this.message = message;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.progress_bar, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.setMessage(message);
    }

    public void setMessage(String message){
        this.message = message;
        if(binding != null) {
            binding.setMessage(message);
        }
    }

    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    /*
    private Dialog dialog;

    public Dialog show(Context context) {
        return show(context, null);
    }

    public Dialog show(Context context, CharSequence title) {
        return show(context, title, false);
    }

    public Dialog show(Context context, CharSequence title, boolean cancelable) {
        return show(context, title, cancelable, null);
    }

    public Dialog show(Context context, CharSequence title, boolean cancelable,
                       DialogInterface.OnCancelListener cancelListener) {
        LayoutInflater inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflator.inflate(R.layout.progress_bar, null);
        if(title != null) {
            final TextView tv = view.findViewById(R.id.id_title);
            tv.setText(title);
        }

        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();

        return dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }
*/
}
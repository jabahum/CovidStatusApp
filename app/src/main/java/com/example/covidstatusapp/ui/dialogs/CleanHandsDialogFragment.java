package com.example.covidstatusapp.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.covidstatusapp.R;
import com.example.covidstatusapp.databinding.CleanHandsDialogBinding;

public class CleanHandsDialogFragment extends DialogFragment {

    private CleanHandsDialogBinding binding;
    private String content;
    private String citattion;

    public CleanHandsDialogFragment(String content, String citattion) {
        this.content = content;
        this.citattion = citattion;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.clean_hands_dialog, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();


    }

    private void init() {
        binding.setContent(content);
        binding.closeCleanHands.setOnClickListener(view -> {
            dismiss();
        });
    }
}

package com.soft918.paintapp.presentation.ui;


import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.BottomSheetBinding;

import javax.inject.Inject;


public class MaterialBottomSheet extends BottomSheetDialogFragment {

    Fragment fragment;
    @Inject
    public MaterialBottomSheet(Fragment fragment){
        this.fragment = fragment;
    }
    private BottomSheetBinding binding;
    public static String TAG = "modalBottomSheet";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BottomSheetBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAddDefaultPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_paintFragment_to_sampleDesignFragment);
                getDialog().dismiss();
            }
        });
        binding.btnAddMemoryPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_paintFragment_to_drawingsDrawnFragment);
                getDialog().dismiss();
            }
        });
        binding.btnAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getDialog().setCancelable(true);
    }
}

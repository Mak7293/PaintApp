package com.soft918.paintapp.presentation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentPaintBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaintFragment extends Fragment {

    public PaintFragment() {
        // Required empty public constructor
    }
    private FragmentPaintBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
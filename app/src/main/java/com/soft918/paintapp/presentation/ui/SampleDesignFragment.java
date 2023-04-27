package com.soft918.paintapp.presentation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentSampleDesignBinding;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SampleDesignFragment extends Fragment {

    public SampleDesignFragment() {
        // Required empty public constructor
    }

    private FragmentSampleDesignBinding binding;
    private MainViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSampleDesignBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
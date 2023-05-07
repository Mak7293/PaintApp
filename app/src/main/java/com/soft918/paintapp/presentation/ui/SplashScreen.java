package com.soft918.paintapp.presentation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentSampleDesignBinding;
import com.soft918.paintapp.databinding.FragmentSplashScreenBinding;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashScreen extends Fragment {


    public SplashScreen() {
        // Required empty public constructor
    }
    private FragmentSplashScreenBinding binding;
    private MainViewModel viewModel;
    private final ScheduledExecutorService backgroundExecutor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreenBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigateToPaintFragment(savedInstanceState);

    }
    private void navigateToPaintFragment(Bundle savedInstanceState){

        new Handler(Looper.getMainLooper())
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavOptions navOptions = new NavOptions
                                .Builder()
                                .setPopUpTo(R.id.splashScreen, true)
                                .build();
                        NavHostFragment
                                .findNavController(SplashScreen.this)
                                .navigate(
                                        R.id.action_splashScreen_to_paintFragment,
                                        savedInstanceState,
                                        navOptions
                                );
                    }
                },
                2500);
    }
}
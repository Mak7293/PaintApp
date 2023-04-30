package com.soft918.paintapp.presentation.ui;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.soft918.paintapp.databinding.FragmentPaintBinding;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import javax.inject.Inject;


public class MaterialBottomSheet extends BottomSheetDialogFragment {

    Fragment fragment;
    MainViewModel viewModel;
    FragmentPaintBinding paintBinding;
    public MaterialBottomSheet(
            Fragment fragment,
            MainViewModel viewModel,
            FragmentPaintBinding paintBinding){
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.paintBinding = paintBinding;
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
                Bitmap bitmap = getBitMapFromView(paintBinding.flPaint);
                viewModel.onEvent(new Event.saveBitmapInDeviceStorage(bitmap));
                getDialog().dismiss();
            }
        });
        binding.btnDeletePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sampleImage = 0;
                paintBinding.ivBackground.setImageDrawable(null);
                getDialog().dismiss();
            }
        });
        getDialog().setCancelable(true);
    }
    private Bitmap getBitMapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if(bgDrawable != null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return (returnedBitmap);
    }
}

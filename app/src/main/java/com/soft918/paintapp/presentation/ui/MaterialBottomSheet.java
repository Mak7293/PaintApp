package com.soft918.paintapp.presentation.ui;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import com.soft918.paintapp.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import com.bumptech.glide.RequestManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.soft918.paintapp.databinding.BottomSheetBinding;
import com.soft918.paintapp.databinding.FragmentPaintBinding;
import com.soft918.paintapp.domain.adapters.PencilAdapter;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.util.Constants;
import com.soft918.paintapp.domain.util.TapTargetView;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MaterialBottomSheet extends BottomSheetDialogFragment {

    private Fragment fragment;
    private MainViewModel viewModel;
    private FragmentPaintBinding paintBinding;
    private RequestManager glide;

    public MaterialBottomSheet(
            Fragment fragment,
            MainViewModel viewModel,
            FragmentPaintBinding paintBinding,
            RequestManager glide
    ){
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.paintBinding = paintBinding;
        this.glide = glide;
    }
    private BottomSheetBinding binding;
    public static String TAG = "modalBottomSheet";
    ActivityResultLauncher<Intent> openGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        if (!Objects.equals(viewModel.drawnImage, "")){
                            viewModel.drawnImage = "";
                        }
                        if(viewModel.sampleImage!=0){
                            viewModel.sampleImage = 0;
                        }
                        viewModel.importedImage = result.getData().getData();
                    }
                }
            });
    ActivityResultLauncher<String> readExternalFileLauncher=
    registerForActivityResult(new ActivityResultContracts.RequestPermission()
            , new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result){
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.
                                Images.Media.EXTERNAL_CONTENT_URI);
                        openGalleryLauncher.launch(pickIntent);
                    }else{
                        alertDialogForDenyingPermission();
                    }
                }
            }
    );

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
        setupRadioGroup();
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
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
                    readExternalFileLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }else{
                    readExternalFileLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        });
        binding.btnAddDrawnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_paintFragment_to_drawingsDrawnFragment);
                getDialog().dismiss();
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
                if (viewModel.sampleImage != 0 ){
                    viewModel.sampleImage = 0;
                }
                if(!Objects.equals(viewModel.drawnImage, "")){
                    viewModel.drawnImage = "";
                }
                if(paintBinding.ivBackground.getDrawable() == null){
                    Toast.makeText(fragment.requireContext(),
                            "تصویری در پس زمینه نقاشی بارگذاری نشده است.",Toast.LENGTH_LONG).show();
                }
                paintBinding.ivBackground.setImageDrawable(null);

                getDialog().dismiss();
            }
        });
        binding.rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getDialog().dismiss();
                if (checkedId == R.id.rbDay){
                    viewModel.current_theme.postValue(Constants.THEME_DAY);
                } else if (checkedId == R.id.rbNight) {
                    viewModel.current_theme.postValue(Constants.THEME_NIGHT);
                }else if (checkedId == R.id.rbDefault) {
                    viewModel.current_theme.postValue(Constants.THEME_DEFAULT);
                }
            }
        });
        binding.btnIntroductionPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                List<View> viewList = new ArrayList<>();
                viewList.add(paintBinding.rvPencil);
                viewList.add(paintBinding.pencil);
                viewList.add(paintBinding.llChangePencilSize);
                viewList.add(paintBinding.eraser);
                viewList.add(paintBinding.llChangeEraserSize);
                viewList.add(paintBinding.btnUndo);
                TapTargetView.PaintFragmentTapTargetView(fragment.requireActivity(),viewList);
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
    private void alertDialogForDenyingPermission(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(fragment.requireContext());
        alertDialog
                .setMessage("برای اضافه کردن تصاویر حافظه داخلی به صفحه نقاشی، باید دسترسی های مورد نیاز را تایید کرده باشید.")
                .setNegativeButton("بازگشت", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("تایید دسترسی ها", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",fragment.requireActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .show();
    }
    private void setupRadioGroup(){
        switch (viewModel.current_theme.getValue()){
            case Constants.THEME_DEFAULT: {
                binding.rbDefault.setChecked(true);
                break;
            }
            case Constants.THEME_DAY: {
                binding.rbDay.setChecked(true);
                break;
            }
            case Constants.THEME_NIGHT: {
                binding.rbNight.setChecked(true);
                break;
            }
        }
    }
}

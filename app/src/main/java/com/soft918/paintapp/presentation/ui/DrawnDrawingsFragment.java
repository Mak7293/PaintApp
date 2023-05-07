package com.soft918.paintapp.presentation.ui;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.util.FileUtil;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.soft918.paintapp.databinding.FragmentDrawingsDrawnBinding;
import com.soft918.paintapp.domain.adapters.DrawnDrawingAdapter;
import com.soft918.paintapp.domain.adapters.SampleDrawingAdapter;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.PaintUriEntity;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DrawnDrawingsFragment extends Fragment {

    public DrawnDrawingsFragment() {
        // Required empty public constructor
    }
    private FragmentDrawingsDrawnBinding binding;
    private MainViewModel viewModel;
    boolean firstTime = true;
    @Inject
    RequestManager glide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDrawingsDrawnBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();
        subscribeToViewModelLiveData();

    }
    private void setupToolbar(){
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(DrawnDrawingsFragment.this).popBackStack();
                }
            });
        }
    }
    private void subscribeToViewModelLiveData(){
        viewModel.getAllPaintPath.observe(getViewLifecycleOwner(), new Observer<List<PaintUriEntity>>() {
            @Override
            public void onChanged(List<PaintUriEntity> paintUriEntities) {
                if(firstTime){
                    validateUriList(paintUriEntities);
                    firstTime = false;
                }
                setupRecyclerView(paintUriEntities);

            }
        });
    }

    private void setupRecyclerView(List<PaintUriEntity> list){
        DrawnDrawingAdapter adapter = new DrawnDrawingAdapter(requireContext(),list,glide);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recyclerView.setAdapter(adapter);
        adapter.onClickListenerSelect(new DrawnDrawingAdapter.OnClickListenerSelect() {
            @Override
            public void onClickSelect(PaintUriEntity paintUriEntity) {
                if (viewModel.sampleImage != 0){
                    viewModel.sampleImage = 0;
                }
                viewModel.drawnImage = paintUriEntity.contentUri;
                NavHostFragment.findNavController(DrawnDrawingsFragment.this).popBackStack();
            }
        });
        adapter.onClickListenerShare(new DrawnDrawingAdapter.OnClickListenerShare() {

            @Override
            public void onClickShare(PaintUriEntity paintUriEntity) {
                shareImage(paintUriEntity);
            }
        });
        adapter.onClickListenerDelete(new DrawnDrawingAdapter.OnClickListenerDelete() {
            @Override
            public void onClickDelete(PaintUriEntity paintUriEntity) {
                viewModel.onEvent(new Event.deletePaintInDb(paintUriEntity));
            }
        });
    }
    private void validateUriList(List<PaintUriEntity> list){
        list.forEach((n) -> {
            File file =  new File(n.fileUri);
            if (!file.exists()){
                viewModel.onEvent(new Event.deletePaintInDb(n));
            }
        });
    }
    private void shareImage(PaintUriEntity paintUriEntity){
        String[] list = {paintUriEntity.fileUri};
        MediaScannerConnection.scanFile(requireContext(), list, null,
                new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent,"share"));
            }
        });
    }
}
package com.soft918.paintapp.presentation.ui;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.RequestManager;
import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentPaintBinding;
import com.soft918.paintapp.domain.adapters.PencilAdapter;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.ColorSet;
import com.soft918.paintapp.domain.util.PencilEraserSize;
import com.soft918.paintapp.domain.util.PencilEraser;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaintFragment extends Fragment {

    @Inject
    public PaintFragment(){
    }
    private FragmentPaintBinding binding;
    private MainViewModel viewModel;
    private  PencilAdapter adapter;
    private boolean firstTime = true;
    private List<ColorSet> colorSetList = new ArrayList<>();
    private int currentColor = Color.BLACK;
    private String pencilSize;
    private String eraserSize;

    @Inject
    RequestManager glide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeToLiveData();

        binding.pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.SelectPencilOrEraser(PencilEraser.pencil.state));
            }
        });
        binding.eraser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.SelectPencilOrEraser(PencilEraser.eraser.state));
            }
        });
        binding.btnUndo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                binding.paintView.onClickUndo();
            }
        });
        binding.btnPositivePencil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.changeSize(
                        sendSizeToViewModel(pencilSize,I_D.increment),
                        PencilEraser.pencil.state
                ));
            }
        });
        binding.btnNegativePencil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.changeSize(
                        sendSizeToViewModel(pencilSize,I_D.decrement),
                        PencilEraser.pencil.state
                ));
            }
        });
        binding.btnPositiveEraser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.changeSize(
                        sendSizeToViewModel(eraserSize,I_D.increment),
                        PencilEraser.eraser.state
                ));
            }
        });
        binding.btnNegativeEraser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewModel.onEvent(new Event.changeSize(
                        sendSizeToViewModel(eraserSize,I_D.decrement),
                        PencilEraser.eraser.state
                ));
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showBottomSheet();
                viewModel.pathList = binding.paintView.getPath();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
        if (!viewModel.pathList.isEmpty()){
            binding.paintView.setPath(viewModel.pathList);
        }
        if(viewModel.sampleImage != 0){
            binding.ivBackground.setImageDrawable(ContextCompat.getDrawable(requireContext(),viewModel.sampleImage));
        }else if(!Objects.equals(viewModel.drawnImage, "")){
            glide.load(Uri.parse(viewModel.drawnImage))
                    .centerInside()
                    .into(binding.ivBackground);
        } else{
            binding.ivBackground.setImageDrawable(null);
        }
    }
    private void showBottomSheet(){
        MaterialBottomSheet modalBottomSheet = new MaterialBottomSheet(
                this,
                viewModel,
                binding,
                glide
        );
        modalBottomSheet.show(getActivity().getSupportFragmentManager(), MaterialBottomSheet.TAG);
    }
    private void subscribeToLiveData(){
        viewModel.colorList.observe(getViewLifecycleOwner(),new Observer<List<ColorSet>>(){
            @Override
            public void onChanged(List<ColorSet> colorSets) {
                colorSetList = colorSets;
                if(firstTime){
                    setupRecyclerView();
                    firstTime = false;
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        viewModel.selectedColor.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                binding.paintView.setColor(color);
                currentColor = color;
            }
        });
        viewModel.selectPencilEraser.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String state) {
                selectPencilOrEraser(state);
            }
        });
        viewModel.pencilSize.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String size) {
                pencilSize = size;
                setPencilSize(size);
            }
        });
        viewModel.eraserSize.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String size) {
                eraserSize = size;
                setEraserSize(size);
            }
        });
    }
    private String sendSizeToViewModel(String size,I_D state){
        if(state == I_D.increment){
            if(size == PencilEraserSize.smallSize.size){
                return PencilEraserSize.mediumSize.size;
            }else if(size == PencilEraserSize.mediumSize.size){
                return PencilEraserSize.largeSize.size;
            }else if(size == PencilEraserSize.largeSize.size){
                return PencilEraserSize.extraLargeSize.size;
            }
        } else if (state == I_D.decrement) {
            if(size == PencilEraserSize.mediumSize.size){
                return PencilEraserSize.smallSize.size;
            }else if(size == PencilEraserSize.largeSize.size){
                return PencilEraserSize.mediumSize.size;
            }else if(size == PencilEraserSize.extraLargeSize.size){
                return PencilEraserSize.largeSize.size;
            }
        }
        return null;
    }
    private void setPencilSize(String size){
        if (size == PencilEraserSize.smallSize.size){
            binding.pencilSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.small_pencil_day));
            binding.paintView.setSizeForBrush(8f);
        }else if(size == PencilEraserSize.mediumSize.size){
            binding.pencilSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.medium_pencil_day));
            binding.paintView.setSizeForBrush(16f);
        }else if(size == PencilEraserSize.largeSize.size){
            binding.pencilSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.large_pencil_day));
            binding.paintView.setSizeForBrush(24f);
        }else if(size == PencilEraserSize.extraLargeSize.size){
            binding.pencilSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.extra_large_pencil_day));
            binding.paintView.setSizeForBrush(32f);
        }
    }
    private void setEraserSize(String size){
        if (size == PencilEraserSize.smallSize.size){
            binding.eraserSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.small_eraser_day));
            binding.paintView.setSizeForBrush(8f);
        }else if(size == PencilEraserSize.mediumSize.size){
            binding.eraserSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.medium_eraser_day));
            binding.paintView.setSizeForBrush(16f);
        }else if(size == PencilEraserSize.largeSize.size){
            binding.eraserSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.large_eraser_day));
            binding.paintView.setSizeForBrush(24f);
        }else if(size == PencilEraserSize.extraLargeSize.size){
            binding.eraserSize.setImageDrawable(ContextCompat
                    .getDrawable(requireContext(),R.drawable.extra_large_eraser_day));
            binding.paintView.setSizeForBrush(32f);
        }
    }
    private void selectPencilOrEraser(String state){
        if (Objects.equals(state, PencilEraser.pencil.state)){
            binding.eraser.setBackground(null);
            binding.pencil.setBackground(ContextCompat
                    .getDrawable(requireContext(),R.drawable.selected_pencil_eraser_background));
            binding.eraser.setTextColor(ContextCompat.getColor(requireContext(),R.color.black_text_color));
            binding.pencil.setTextColor(ContextCompat.getColor(requireContext(),R.color.white_text_color));
            binding.paintView.setColor(currentColor);
            setPencilSize(pencilSize);
        } else if (Objects.equals(state, PencilEraser.eraser.state)) {
            binding.pencil.setBackground(null);
            binding.eraser.setBackground(ContextCompat
                    .getDrawable(requireContext(),R.drawable.selected_pencil_eraser_background));
            binding.pencil.setTextColor(ContextCompat.getColor(requireContext(),R.color.black_text_color));
            binding.eraser.setTextColor(ContextCompat.getColor(requireContext(),R.color.white_text_color));
            binding.paintView.setColor(Color.WHITE);
            setEraserSize(eraserSize);
        }
    }
    private void setupRecyclerView(){
        adapter = new PencilAdapter(requireContext(),colorSetList);
        binding.rvPencil.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false));
        binding.rvPencil.setAdapter(adapter);
        adapter.onClickListenerSelect(new PencilAdapter.OnClickListenerSelect() {
            @Override
            public void onClickSelect(int id) {
                viewModel.onEvent(new Event.UpdateColorList(id));
                selectPencilOrEraser(PencilEraser.pencil.state);
            }
        });
    }
    enum I_D{
        increment,
        decrement
    }
}
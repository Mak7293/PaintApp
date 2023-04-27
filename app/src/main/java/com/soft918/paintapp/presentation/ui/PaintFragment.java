package com.soft918.paintapp.presentation.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentPaintBinding;
import com.soft918.paintapp.domain.adapters.PencilAdapter;
import com.soft918.paintapp.domain.component.Pencil;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.ColorSet;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaintFragment extends Fragment {

    public PaintFragment() {
        // Required empty public constructor
    }
    private FragmentPaintBinding binding;
    private MainViewModel viewModel;
    private  PencilAdapter adapter;
    private boolean firstTime = true;
    private List<ColorSet> colorSetList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeToLiveData();
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
            public void onChanged(Integer integer) {
                binding.paintView.setColor(integer);
            }
        });
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
            }
        });
    }

}
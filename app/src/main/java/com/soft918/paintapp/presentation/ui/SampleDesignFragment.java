package com.soft918.paintapp.presentation.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentSampleDesignBinding;
import com.soft918.paintapp.domain.adapters.SampleDrawingAdapter;
import com.soft918.paintapp.domain.util.TapTargetView;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SampleDesignFragment extends Fragment {

    @Inject
    public SampleDesignFragment() {
        // Required empty public constructor
    }

    private FragmentSampleDesignBinding binding;
    private MainViewModel viewModel;
    private Menu menu;

    private SampleDrawingAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSampleDesignBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();
        setupRecyclerView();
    }
    private void setupToolbar(){
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(SampleDesignFragment.this).popBackStack();
                }
            });
        }
    }
    private void setupRecyclerView(){
        List<Integer> list = provideRecyclerViewList();
        adapter = new SampleDrawingAdapter(requireContext(),list);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recyclerView.setAdapter(adapter);
        adapter.onClickListenerSelect(new SampleDrawingAdapter.OnClickListenerSelect() {
            @Override
            public void onClickSelect(int resId) {
                if(!Objects.equals(viewModel.drawnImage, "")){
                    viewModel.drawnImage = "";
                }
                if (viewModel.importedImage!=null){
                    viewModel.importedImage = null;
                }
                viewModel.sampleImage = resId;
                NavHostFragment.findNavController(SampleDesignFragment.this).popBackStack();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.menu_information,menu);
        this.menu = menu;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        int currentNightMode = getResources().getConfiguration()
                .uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            menu.getItem(0).getIcon().setTint(
                    ContextCompat.getColor(requireContext(),R.color.num2_pallet_one));
        }else{
            menu.getItem(0).getIcon().setTint(
                    ContextCompat.getColor(requireContext(),R.color.white_text_color));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.information) {
            TapTargetView.SampleDesignFragmentTapTargetView(requireActivity(),adapter.designView);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Integer> provideRecyclerViewList(){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.img_1);
        list.add(R.drawable.img_2);
        list.add(R.drawable.img_3);
        list.add(R.drawable.img_4);
        list.add(R.drawable.img_5);
        list.add(R.drawable.img_6);
        list.add(R.drawable.img_7);
        list.add(R.drawable.img_8);
        list.add(R.drawable.img_9);
        list.add(R.drawable.img_10);
        list.add(R.drawable.img_11);
        list.add(R.drawable.img_12);
        list.add(R.drawable.img_13);
        list.add(R.drawable.img_14);
        list.add(R.drawable.img_15);
        list.add(R.drawable.img_16);
        list.add(R.drawable.img_17);
        list.add(R.drawable.img_18);
        list.add(R.drawable.img_19);
        list.add(R.drawable.img_20);
        list.add(R.drawable.img_22);
        list.add(R.drawable.img_23);
        list.add(R.drawable.img_24);
        list.add(R.drawable.img_25);
        list.add(R.drawable.img_26);
        list.add(R.drawable.img_27);
        return list;
    }
}
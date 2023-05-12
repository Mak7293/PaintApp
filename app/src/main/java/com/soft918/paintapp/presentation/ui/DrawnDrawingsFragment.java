package com.soft918.paintapp.presentation.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import com.bumptech.glide.RequestManager;
import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.FragmentDrawingsDrawnBinding;
import com.soft918.paintapp.domain.adapters.DrawnDrawingAdapter;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.PaintUriEntity;
import com.soft918.paintapp.domain.util.TapTargetView;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;
import java.io.File;
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
    private Menu menu;
    private DrawnDrawingAdapter adapter;
    @Inject
    RequestManager glide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDrawingsDrawnBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        setHasOptionsMenu(true);
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
        adapter = new DrawnDrawingAdapter(requireContext(),list,glide);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recyclerView.setAdapter(adapter);
        adapter.onClickListenerSelect(new DrawnDrawingAdapter.OnClickListenerSelect() {
            @Override
            public void onClickSelect(PaintUriEntity paintUriEntity) {
                if (viewModel.sampleImage != 0){
                    viewModel.sampleImage = 0;
                }
                if (viewModel.importedImage!=null){
                    viewModel.importedImage = null;
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
            if (viewModel.getAllPaintPath.getValue().size()!=0){
                List<View> viewList = new ArrayList<>();
                viewList.add(adapter.btn_select_view);
                viewList.add(adapter.btn_delete_view);
                viewList.add(adapter.btn_share_view);
                TapTargetView.DrawnDrawingFragmentTapTargetView(requireActivity(),viewList);
            }else{
                Toast.makeText(requireContext(),"ابتدا باید اقدام به ذخیره سازی نقاشی از منو اصلی نمایید.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
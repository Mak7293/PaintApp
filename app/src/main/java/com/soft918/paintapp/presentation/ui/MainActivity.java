package com.soft918.paintapp.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.soft918.paintapp.databinding.ActivityMainBinding;
import com.soft918.paintapp.domain.util.Constants;
import com.soft918.paintapp.domain.util.Theme;
import com.soft918.paintapp.presentation.viewmodel.MainViewModel;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    @Inject
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        subscribeToLiveData();

    }
    private void subscribeToLiveData(){
        viewModel.current_theme.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case Constants.THEME_DEFAULT: {
                        Theme.setTheme(Constants.THEME_DEFAULT,sharedPref);
                        break;
                    }
                    case Constants.THEME_DAY: {
                        Theme.setTheme(Constants.THEME_DAY,sharedPref);
                        break;
                    }
                    case Constants.THEME_NIGHT: {
                        Theme.setTheme(Constants.THEME_NIGHT,sharedPref);
                        break;
                    }
                }
            }
        });
    }
}
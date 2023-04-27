package com.soft918.paintapp.presentation.viewmodel;


import android.app.Application;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soft918.paintapp.R;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.ColorSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    Application application;
    public MutableLiveData<List<ColorSet>> colorList = new MutableLiveData<List<ColorSet>>();
    public MutableLiveData<Integer> selectedColor = new MutableLiveData<Integer>();
    @Inject
    public MainViewModel(Application application){
        this.application = application;
        colorList.postValue(provideListOfColorSet());
    }

    public void onEvent(Event event){
        if (event instanceof Event.UpdateColorList){
            updateColorList(((Event.UpdateColorList) event).listId);
        }
    }
    private  void updateColorList(int id){
        List<ColorSet> list = colorList.getValue();
        assert list != null;
        list.forEach((n) -> {
            n.isPicked = false;
        });
        if (!list.get(id).isPicked){
            list.get(id).isPicked = true;
        }
        colorList.postValue(list);
        selectedColor.postValue(list.get(id).color);
    }
    private List<ColorSet> provideListOfColorSet(){
        List<ColorSet> list = new ArrayList<ColorSet>();
        list.add(new ColorSet(ContextCompat.getColor(application, R.color.red_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.red_orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.golden_yellow_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.peach_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.aqua_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.sky_blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.light_blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.violet_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.brown_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.light_brown_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.mahogany_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.magenta_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.pink_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.jade_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.gray_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.tan_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.black),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.white),false));
        return list;
    }

}

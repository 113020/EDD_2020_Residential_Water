package com.example.edd_2020_residential_water;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.edd_2020_residential_water.Splash;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Splash>> selected = new MutableLiveData<List<Splash>>();

    public void select(List<Splash> w) {
        selected.setValue(w);
    }

    public LiveData<List<Splash>> getSelected() {
        return selected;
    }
}

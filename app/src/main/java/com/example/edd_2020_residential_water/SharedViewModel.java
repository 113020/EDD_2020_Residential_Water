package com.example.edd_2020_residential_water;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.edd_2020_residential_water.models.Water;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Water>> selected = new MutableLiveData<List<Water>>();

    public void select(List<Water> w) {
        selected.setValue(w);
    }

    public LiveData<List<Water>> getSelected() {
        return selected;
    }
}

package com.example.edd_2020_residential_water;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class WaterViewModel extends AndroidViewModel {

    private WaterRepository mRepository;

    private List<Water> mAllSplashes;

    public WaterViewModel(Application application) {
        super(application);
        mRepository = new WaterRepository(application);
        mAllSplashes = mRepository.getAllSplashes();
    }

    public void insert(Water water) { mRepository.insert(water); }

    public void update(Water water) { mRepository.update(water); }

    public void delete(Water water) { mRepository.delete(water); }

    public List<Water> getAllSplashes() { return mAllSplashes; }

    public List<Water> getByFixture(String fixture) { return mRepository.getByFixture(fixture); }

    public List<Water> getByBillMethod(String method) { return mRepository.getByBillMethod(method); }

    public void deleteAll() { mRepository.deleteAll(); }
}

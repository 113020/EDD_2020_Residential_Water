package com.example.edd_2020_residential_water;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class WaterViewModel extends AndroidViewModel {

private WaterRepository mRepository;

    private List<Splash> mAllSplashes;

    public WaterViewModel(Application application) {
        super(application);
        mRepository = new WaterRepository(application);
        mAllSplashes = mRepository.getAllSplashes();
    }

    /*public void insert(Water water) { mRepository.insert(waterOld); }

    public void update(Water water) { mRepository.update(waterOld); }

    public void delete(Water water) { mRepository.delete(waterOld); }*/

    public List<Splash> getAllSplashes() { return mAllSplashes; }

    List<Splash> getByFixture(String fixture) { return mRepository.getByFixture(fixture); }

    List<Splash> getByTimeInterval(String interval) { return mRepository.getByTimeInterval(interval); }

    List<Splash> getByBillMethod(String method) { return mRepository.getByBillMethod(method); }


}

package com.example.edd_2020_residential_water;

import android.app.Application;

import java.util.List;

public class WaterRepository {
private WaterDao mWaterDao;
    private List<Splash> mAllSplashes;
    private String fixture;
    private String interval;
    private String method;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public WaterRepository(Application application) {
        WaterDatabase db = WaterDatabase.getDatabase(application);
        mWaterDao = db.waterDao();
        mAllSplashes = mWaterDao.getAllSplashes();
    }

    // You must call these methods on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
//    public void insert(Water waterOld) {WaterDatabase.databaseWriteExecutor.execute(() -> {mWaterDao.insertSplash(waterOld);});}

    /*public void insert(Water water) {WaterDatabase.databaseWriteExecutor.execute(water::getFixture);}
    public void update(Water water) {WaterDatabase.databaseWriteExecutor.execute(water::getFixture);}
    public void delete(Water water) {WaterDatabase.databaseWriteExecutor.execute(water::getFixture);}*/

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    List<Splash> getAllSplashes() {
        return mAllSplashes;
    }

    List<Splash> getByFixture(String fixture) {
        return mWaterDao.getByFixture(fixture);
    }

    List<Splash> getByTimeInterval(String interval) {
        return mWaterDao.getByTimeInterval(interval);
    }

    List<Splash> getByBillMethod(String method) {
        return mWaterDao.getByBillMethod(method);
    }

    void deleteAll() {
        mWaterDao.deleteAll();
    }

}

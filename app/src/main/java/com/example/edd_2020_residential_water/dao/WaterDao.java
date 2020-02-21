package com.example.edd_2020_residential_water.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.edd_2020_residential_water.models.Splash;

import java.util.List;

/**
 * This interface creates methods that conduct SQL queries, which are requests for accessing or modifying data
 * in any way, shape, or form. Room has four basic methods fo queries, which are Insert, Update,
 * Delete, and Query, all of which have their respective "@" tags. However, there are more advanced data tools
 * that you can easily add. However, for this framework purpose, stick with the basic ones for now.
 */
@Dao
public interface WaterDao {
    // Insert parameter Splash database entity into the Water Database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSplash(Splash splash);

    // Update Splash database entity, given as parameter, that matches with the primary key of each database entity
    @Update
    public void updateSplash(Splash splash);

    // Delete (from the table) the Splash database entity, given as parameter, that matches with the primary key of each database entity
    @Delete
    public void deleteSplash(Splash splash);

    @Query("SELECT * FROM splash")
    public List<Splash> getAllSplashes();

    @Query("SELECT * FROM splash WHERE fixture=:fixture")
    public List<Splash> getByFixture(String fixture);

    @Query("SELECT * FROM splash WHERE time_interval=:interval")
    public List<Splash> getByTimeInterval(String interval);

    @Query("SELECT * FROM splash WHERE water_bill_method=:method")
    public List<Splash> getByBillMethod(String method);
}

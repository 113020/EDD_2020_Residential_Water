package com.example.edd_2020_residential_water;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This interface creates methods that conduct SQL queries, which are requests for accessing or modifying data
 * in any way, shape, or form. Room has four basic methods fo queries, which are Insert, Update,
 * Delete, and Query, all of which have their respective "@" tags. However, there are more advanced data tools
 * that you can easily add. However, for this framework purpose, stick with the basic ones for now.
 */
@Dao
public interface WaterDao {
    // Insert parameter Water database entity into the Water Database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWater(Water water);

    // Update Water database entity, given as parameter, that matches with the primary key of each database entity
    @Update
    public void updateWater(Water water);

    // Delete (from the table) the Water database entity, given as parameter, that matches with the primary key of each database entity
    @Delete
    public void deleteWater(Water water);

    // Get all the Water entities
    @Query("SELECT * FROM water")
    public List<Water> getAllSplashes();

    // Get all the Water entities that correspond with the fixture parameter
    @Query("SELECT * FROM water WHERE fixture=:fixture")
    public List<Water> getByFixture(String fixture);

    // Get all the Water entities that correspond with water bill calculation method
    @Query("SELECT * FROM water WHERE water_bill_method=:method")
    public List<Water> getByBillMethod(String method);

    @Query("DELETE FROM water")
    public void deleteAll();
}
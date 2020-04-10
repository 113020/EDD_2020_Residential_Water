package com.example.edd_2020_residential_water;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ForkJoinPool;

@Database(entities = {Water.class}, version = 1, exportSchema = false)
public abstract class WaterDatabase extends RoomDatabase {
    public static ForkJoinPool databaseWriteExecutor;

    public abstract WaterDao waterDao();
    public static final String DB_NAME = "water";

    public static WaterDatabase getDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, WaterDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build(); //build database
    }
}

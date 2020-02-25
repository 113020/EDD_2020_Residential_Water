package com.example.edd_2020_residential_water.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.edd_2020_residential_water.models.Splash;
import com.example.edd_2020_residential_water.dao.WaterDao;

@Database(entities = {Splash.class}, version = 2, exportSchema = false)
public abstract class WaterDatabase extends RoomDatabase {
    public abstract WaterDao waterDao();
    public static final String DB_NAME = "storm";

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE whooshes ADD COLUMN climbSecs INTEGER");
        }
    };

    public static WaterDatabase getDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, WaterDatabase.class, DB_NAME)
                .addMigrations(WaterDatabase.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build(); //build database
    }
}

package com.example.edd_2020_residential_water;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ForkJoinPool;

@Database(entities = {Water.class}, version = 3, exportSchema = false)
public abstract class WaterDatabase extends RoomDatabase {
    public static ForkJoinPool databaseWriteExecutor;

    public abstract WaterDao waterDao();
    public static final String DB_NAME = "water";

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE splashes ADD COLUMN climbSecs INTEGER");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE water RENAME ROW water_speed TO flow_rate");
//            database.execSQL("ALTER TABLE water RENAME COLUMN extent_of_use TO extent");
            database.execSQL("ALTER TABLE water RENAME ROW water_speed TO flow_rate");
        }
    };

    public static WaterDatabase getDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, WaterDatabase.class, DB_NAME)
                .addMigrations(WaterDatabase.MIGRATION_1_2, WaterDatabase.MIGRATION_2_3)
                .allowMainThreadQueries()
                .build(); //build database
    }
}
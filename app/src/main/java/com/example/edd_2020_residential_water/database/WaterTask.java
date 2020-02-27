package com.example.edd_2020_residential_water.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.edd_2020_residential_water.dao.WaterDao;
import com.example.edd_2020_residential_water.models.Splash;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

/**
 * This is the java class that handles the tasks using the AsyncTask.
 * It chooses the tasks to run in the background, if any,
 * chooses data updating tasks, if any, and what to do after background tasks finish.
 */
public class WaterTask extends AsyncTask<String, Integer, Boolean> {


    WaterDatabase waterdb;
    WeakReference<Context> context;

    public WaterTask(WaterDatabase db, Context context) {
        super();
        waterdb = db;
        this.context = new WeakReference<>(context);
    }

    protected Boolean doInBackground(String... arduino) {

        Splash w;

        WaterDao waterDao = waterdb.waterDao();

        String[] splash = arduino[0].split(Pattern.quote("|"));
        String[] splashSub;

        try {
            for (String s: splash) {
                splashSub = s.split(Pattern.quote(","));
                w = new Splash();
                w.setDate(splashSub[0]);
                w.setTime(splashSub[1]);
                w.setFixture(splashSub[2]);
                w.setWaterSpeed(Double.parseDouble(splashSub[3]));
                w.setWaterExtent(Double.parseDouble(splashSub[4]));
                w.setTimeInterval(splashSub[5]);
                w.setHourlyFrequency(Integer.parseInt(splashSub[6]));
                w.setDailyFrequency(Integer.parseInt(splashSub[7]));
                w.setWeeklyFrequency(Integer.parseInt(splashSub[8]));
                w.setMonthlyFrequency(Integer.parseInt(splashSub[9]));
                w.setYearlyFrequency(Integer.parseInt(splashSub[10]));
                w.setLeakFrequency(Integer.parseInt(splashSub[11]));
                w.setBillMethod(splashSub[12]);
                w.setWaterBill(Double.parseDouble(splashSub[13]));
                w.setWaterFact(splashSub[14]);
                waterDao.insertSplash(w);
            }

        } catch (Exception e) {
            Log.d("Failed", e.toString());
            return false;
        }
        return true;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Boolean result) {
        if (context.get() != null) {
            Toast.makeText(context.get(), result ? "QR Scanned Successfully" : "Scan Unsucessful", Toast.LENGTH_SHORT).show();
        }
    }
}

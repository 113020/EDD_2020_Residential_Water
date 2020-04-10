package com.example.edd_2020_residential_water;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

public class ResultTask extends AsyncTask<String, Integer, Boolean> {
    WaterDatabase db;
    WeakReference<Context> context;

    public ResultTask(WaterDatabase db, Context context) {
        super();
        this.db = db;
        this.context = new WeakReference<>(context);
    }

    protected Boolean doInBackground(String... intake) {

        Water w;

        WaterDao waterDao = db.waterDao();

        String[] water = intake[0].split(Pattern.quote("|"));
        String[] waterSub;

        try {
            for (String s : water) {
                waterSub = s.split(Pattern.quote(","));
                w = new Water();
                w.setDate(waterSub[0]);
                w.setTime(waterSub[1]);
                w.setFixture(waterSub[2]);
                w.setFlowRate(Double.parseDouble(waterSub[3]));
                w.setExtent(Double.parseDouble(waterSub[4]));
                w.setLeak(Boolean.parseBoolean(waterSub[5]));
                w.setVolumeFlow(Double.parseDouble(waterSub[6]));
                w.setBillMethod(waterSub[7]);
                w.setWaterBill(Double.parseDouble(waterSub[8]));
                w.setWaterFact(waterSub[9]);
                waterDao.insertWater(w);
            }

        } catch (Exception e) {
            Log.d("Failed", e.toString());
            return false;
        }
        return true;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Boolean result) {
        //showDialog("Downloaded " + result + " bytes");
//        Toast t = new Toast(context);
//        t.makeText()
//        if (context.get() != null) {
//            Toast.makeText(context.get(), result ? "QR Scanned Successfully" : "Scan Unsuccessful", Toast.LENGTH_SHORT).show();
//        }
    }
}

package com.example.edd_2020_residential_water;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.intaking.Intake;
import com.example.edd_2020_residential_water.interval.Interval;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.waterBill.WaterBill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.edd_2020_residential_water.ui.main.SectionsPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Intake.OnFragmentInteractionListener, Fixtures.OnFragmentInteractionListener,
        Interval.OnFragmentInteractionListener, WaterBill.OnFragmentInteractionListener {

    private List<Water> list;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken(); // Get new Instance ID token
                String msg = getString(R.string.msg_token_fmt, token); // Log and toast
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Water> initWaters() {
        list = new ArrayList<>();
        String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        list.add(new Water(14,3,2019, 5, 00, 00, fixtureOpt[1], 40.0, 12, true, 0));
        list.add(new Water(14,3,2019, 7, 00, 00, fixtureOpt[2], 30.0, 12, true, 0));
        list.add(new Water(15,2,2019, 8, 59, 59, fixtureOpt[1], 25.0, 12, true, 0));
        list.add(new Water(15,2,2019, 9, 59, 59, fixtureOpt[2], 25.0, 12, true, 0));

        for (Water water: list) { water.setVolumeFlow(water.getFlowRate() * water.getSecondExtent()); }
        return list;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void clearWaterList(List<Water> wl) { wl.clear(); }
    public void setWaterList(List<Water> wl) { wl.addAll(wl); }
}
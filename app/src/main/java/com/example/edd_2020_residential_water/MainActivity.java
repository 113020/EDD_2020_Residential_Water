package com.example.edd_2020_residential_water;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.intaking.Intake;
import com.example.edd_2020_residential_water.intaking.IntakeRecyclerViewAdapter;
import com.example.edd_2020_residential_water.interval.Interval;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.waterBill.WaterBill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.edd_2020_residential_water.ui.main.SectionsPagerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Intake.OnFragmentInteractionListener, Fixtures.OnFragmentInteractionListener,
        Interval.OnFragmentInteractionListener, WaterBill.OnFragmentInteractionListener {

    private List<Water> list;
    public static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FirebaseApp.initializeApp(this, Objects.requireNonNull(FirebaseOptions.fromResource(this)));

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

        list.add(new Water(14,3,2019, 6, 00, 00, fixtureOpt[1], 40.0, 00,12, true, 36000));
        list.add(new Water(14,3,2019, 7, 00, 00, fixtureOpt[2], 30.0, 00,12, true, 28000));
        list.add(new Water(15,2,2019, 8, 00, 00, fixtureOpt[1], 25.0, 00,12, true, 39600));
        list.add(new Water(15,2,2019, 9, 00, 00, fixtureOpt[2], 25.0, 00,12, true, 32400));

        return list;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void clearWaterList(List<Water> wl) { wl.clear(); }
    public void setWaterList(List<Water> wl) { wl.addAll(wl); }
}
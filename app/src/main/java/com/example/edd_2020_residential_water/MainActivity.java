package com.example.edd_2020_residential_water;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Scan.OnFragmentInteractionListener, Fixtures.OnFragmentInteractionListener, Overall.OnFragmentInteractionListener, WaterBill.OnFragmentInteractionListener {

    public static final int DUMP_REQUEST_CODE = 2729;
    public WaterDatabase waterdb;
    public String[] waterdbCols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /* get database, or build if it doesn't exist. This exact line must be included in the onCreate
        method of every Activity that uses the database. waterdb can be a class-wide variable or local
        within onCreate. */
        waterdb = WaterDatabase.getDatabase(getApplicationContext());
    }

    public List<Splash> initWaters() {
        List<Splash> list = new ArrayList<>();
        String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        list.add(new Splash("12/2/19", "6:00", fixtureOpt[1], 25.0, 12.5, "hourly",
                25, 25, 25, 25, 25, 2,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Splash("12/3/19", "6:00", fixtureOpt[2], 25.0, 12.5, "hourly",
                25, 25, 25, 25, 25, 2,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Splash("12/4/19", "6:00", fixtureOpt[3], 25.0, 12.5, "hourly",
                25, 25, 25, 25, 25, 2,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Splash("12/5/19", "6:00", fixtureOpt[4], 25.0, 12.5, "hourly",
                25, 25, 25, 25, 25, 2,
                "regular", 40.0, "Save 20% of water."));

        return list;
    }

    /**
     * Get the table column names
     * @return waterdbCols
     */
    public String[] getColNames() {
        return waterdbCols;
    }

    @Override
    public List<Splash> getByFixture(String fixture) {
        WaterDao waterDao = waterdb.waterDao();
        return waterDao.getByFixture(fixture);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
package com.example.edd_2020_residential_water;

import android.net.Uri;
import android.os.Bundle;

import com.example.edd_2020_residential_water.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Intake.OnFragmentInteractionListener, Fixtures.OnFragmentInteractionListener,
        Interval.OnFragmentInteractionListener, WaterBill.OnFragmentInteractionListener {

    private WaterDatabase waterdb;
    private String[] waterdbCols;
    private WaterDao waterDao;
    private List<Water> list;

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
        waterDao = waterdb.waterDao();
    }

    public List<Water> initWaters() {
        list = new ArrayList<>();
        String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        list.add(new Water("12/2/19", "6:00", fixtureOpt[1], 25.0, 12.5,true,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Water("12/3/19", "6:00", fixtureOpt[2], 25.0, 12.5,true,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Water("12/4/19", "6:00", fixtureOpt[3], 25.0, 12.5,true,
                "regular", 40.0, "Save 20% of water."));
        list.add(new Water("12/5/19", "6:00", fixtureOpt[4], 25.0, 12.5,true,
                "regular", 40.0, "Save 20% of water."));

        return list;
    }

    public void clearWaterList(List<Water> wl) {
        list.clear();
    }

    public void setWaterList(List<Water> wl) {
        list.addAll(wl);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public List<Water> getByFixture(String fixture) {
        return waterDao.getByFixture(fixture);
    }

    @Override
    public List<Water> getAllSplashes() {
        return waterDao.getAllSplashes();
    }
}
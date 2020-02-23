package com.example.edd_2020_residential_water.activities;

import android.net.Uri;
import android.os.Bundle;

import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.tabs.Fixtures;
import com.example.edd_2020_residential_water.tabs.Overall;
import com.example.edd_2020_residential_water.tabs.Scan;
import com.example.edd_2020_residential_water.tabs.WaterBill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.edd_2020_residential_water.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements Scan.OnFragmentInteractionListener, Fixtures.OnFragmentInteractionListener, Overall.OnFragmentInteractionListener, WaterBill.OnFragmentInteractionListener {

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

        // Spinner object for the spinner container in the fixtures fragment
        /*Spinner chooseFixture = (Spinner) findViewById(R.id.enterFixture);

        // Initializing an ArrayAdapter. Also important for custom text size, color, font, etc.through "spinner_fixture"
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fixture, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        chooseFixture.setAdapter(adapter);*/

        // Assigning string array to the string resource file array under the "values" directory
//        String[] fixtureOpts = getResources().getStringArray(R.array.fixture);

        /*ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_fixture, fixtureOpts);
        spinnerArrayAdapter1
        ArrayAdapter<CharSequence> spinnerArrayAdapter2 = ArrayAdapter.createFromResource(this,
                R.array.fixture, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_fixture);
        chooseFixture.setAdapter(spinnerArrayAdapter2);
        super.onCreate(savedInstanceState);*/

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
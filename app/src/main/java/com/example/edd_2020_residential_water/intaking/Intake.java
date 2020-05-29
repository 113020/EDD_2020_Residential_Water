package com.example.edd_2020_residential_water.intaking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.databinding.FragmentIntakeBinding;
import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.fixtures.FixturesRecyclerViewAdapter;
import com.example.edd_2020_residential_water.interval.IntervalRecyclerViewAdapter;
import com.example.edd_2020_residential_water.models.Bill;
import com.example.edd_2020_residential_water.models.FixturePercentage;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.waterBill.WaterBillRecyclerViewAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Intake#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Intake extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1, mParam2;

    List<Water> waterList, list;
    List<FixturePercentage> fixturePercentages;
    List<Bill> bills;

    private FragmentIntakeBinding waterBinding;
    private IntakeRecyclerViewAdapter mAdapterI;
    private FixturesRecyclerViewAdapter mAdapterF;
    private IntervalRecyclerViewAdapter mAdapterT;
    private WaterBillRecyclerViewAdapter mAdapterB;
    private RecyclerView fluidI, fluidF, fluidT, fluidWB;
    private MainActivity conserve;
    private View view;

    @Inject
    private WeakReference <LinearLayoutManager> llmI, llmF, llmT, llmWB;

    private ConstraintLayout constraintLayoutFixtures, constraintLayoutInterval, constraintLayoutWaterBill;

    private SharedViewModel svm;
    private OnFragmentInteractionListener mListener;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mGetReference = mDatabase.getReference().child("Master Shower");

    public static final String CHANNEL_ID = "simple_water_message";

    public Intake() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Intake.
     */
    // TODO: Rename and change types and number of parameters
    public static Intake newInstance(String param1, String param2) {
        Intake fragment = new Intake();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_intake, container, false);
        final View view = waterBinding.getRoot();

        constraintLayoutFixtures = waterBinding.fixtureL;
        constraintLayoutInterval = waterBinding.intervalL;
        constraintLayoutWaterBill = waterBinding.waterBillL;

        llmI = new WeakReference<LinearLayoutManager>(new LinearLayoutManager(view.getContext()));
        llmF = new WeakReference<LinearLayoutManager>(new LinearLayoutManager(view.getContext()));
        llmT = new WeakReference<LinearLayoutManager>(new LinearLayoutManager(view.getContext()));
        llmWB = new WeakReference<LinearLayoutManager>(new LinearLayoutManager(view.getContext()));

        waterBinding.waterDataIntake.setLayoutManager(llmI.get());
        waterBinding.fixtureData.setLayoutManager(llmF.get());
        waterBinding.waterDataInterval.setLayoutManager(llmT.get());
        waterBinding.billData.setLayoutManager(llmWB.get());

        final Water water = new Water();
        waterList = new ArrayList<Water>();
        waterList.clear();

        constraintLayoutFixtures.setVisibility(View.INVISIBLE);
        constraintLayoutInterval.setVisibility(View.INVISIBLE);
        constraintLayoutWaterBill.setVisibility(View.INVISIBLE);

//        FirebaseDatabase.getInstance().getReference().child("Master Shower").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //reading data from firebase database
        /*FirebaseDatabase.getInstance().getReference().child("Master Shower").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                water.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                water.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
                water.setYear(Calendar.getInstance().get(Calendar.YEAR));
                water.setHour(Calendar.getInstance().get(Calendar.HOUR));
                water.setMinute(Calendar.getInstance().get(Calendar.MINUTE));
                water.setSecond(Calendar.getInstance().get(Calendar.SECOND));
                water.setFixture(dataSnapshot.getKey().toString() != null ? dataSnapshot.getKey().toString() : "");
                water.setFlowRateL(dataSnapshot.child("flowL").getValue() != null ?
                        Double.parseDouble(dataSnapshot.child("flowL").getValue().toString()) : 0);
                water.setFlowRateML(dataSnapshot.child("flowML").getValue() != null ?
                        Double.parseDouble(dataSnapshot.child("flowML").getValue().toString()) : 0);
                water.setSecondExtent(0);
                water.setLeak(dataSnapshot.child("Leaking").getValue() != null ?
                        Boolean.parseBoolean(dataSnapshot.child("Leaking").getValue().toString()) : false);
                water.setVolumeFlow(dataSnapshot.child("totalVolume").getValue() != null ?
                        Double.parseDouble(dataSnapshot.child("totalVolume").getValue().toString()) : 0);
                waterList.add(water);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        FirebaseDatabase.getInstance().getReference().child("Master Shower").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDatabase.getInstance().getReference().child("Master Shower").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                waterList.clear();
                        Water water = new Water();
                        water.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        water.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
                        water.setYear(Calendar.getInstance().get(Calendar.YEAR));
                        water.setHour(Calendar.getInstance().get(Calendar.HOUR));
                        water.setMinute(Calendar.getInstance().get(Calendar.MINUTE));
                        water.setSecond(Calendar.getInstance().get(Calendar.SECOND));
                        water.setFixture(dataSnapshot.child("Fixture").getValue() != null ? dataSnapshot.child("Fixture").getValue().toString() : "");
                        water.setFlowRateL(dataSnapshot.child("flowL").getValue() != null ?
                                Double.parseDouble(dataSnapshot.child("flowL").getValue().toString()) : 0);
                        water.setFlowRateML(dataSnapshot.child("flowML").getValue() != null ?
                                Double.parseDouble(dataSnapshot.child("flowML").getValue().toString()) : 0);
                        water.setSecondExtent(0);
                        water.setLeak(dataSnapshot.child("Leaking").getValue() != null ?
                                Boolean.parseBoolean(dataSnapshot.child("Leaking").getValue().toString()) : false);
                        water.setVolumeFlow(dataSnapshot.child("totalVolume").getValue() != null ?
                                Double.parseDouble(dataSnapshot.child("totalVolume").getValue().toString()) : 0);
                        waterList.add(water);

                        mAdapterI = new IntakeRecyclerViewAdapter(water);

                        // Set the layout manager
                        waterBinding.setWaterManagerI(llmI.get());

                        // Set the adapter
                        waterBinding.setWaterAdapterI(mAdapterI);

                        final List<String> fixtureOpt = new ArrayList<String>();
                        fixtureOpt.add("Choose a fixture.");

                        String fix = waterList.get(0).getFixture();
                        fixtureOpt.add(fix);

                        int h = 1;

                        for (Water w: waterList) {
                            if (water.getFixture() != fix) {
                                fixtureOpt.add(water.getFixture());
                                h++;
                            } else {
                                Toast.makeText(view.getContext(), fixtureOpt.get(h), Toast.LENGTH_SHORT).show();
                            }
                        }

                        FragmentManager fm = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(waterBinding.waterDataIntake.getId(), Fixtures.newInstance("", ""));

                        final int[] arraySpinner = {0, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
                        final String[] layouts = new String[arraySpinner.length];
                        layouts[0] = "Which option?";

                        for (int i = 1; i < layouts.length; i++) {
                            layouts[i] = getResources().getString(arraySpinner[i]);
                        }

                        final Spinner layoutSpinner = waterBinding.enterLayout;
                        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, layouts);
                        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        layoutSpinner.setAdapter(adapterL);

                        layoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (arraySpinner[position] == R.string.tab_text_2) {
                                    constraintLayoutFixtures.setVisibility(View.VISIBLE);
                                    constraintLayoutInterval.setVisibility(View.INVISIBLE);
                                    constraintLayoutWaterBill.setVisibility(View.INVISIBLE);

                                    final Spinner fixtureSpin = waterBinding.enterFixture;
                                    list = new ArrayList<Water>();
                                    fixturePercentages = new ArrayList<FixturePercentage>();
                                    waterBinding.fixtureData.removeAllViews();

                                    // Add spinner and array adapter
                                    ArrayAdapter<String> adapterF = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, fixtureOpt);
                                    adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    waterBinding.setFixtureAdapter(adapterF);

                                    // Create the listener for the spinner: responsible for getting the list based on the option
                                    fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                            Toast.makeText(v.getContext(), fixtureOpt.get(position), Toast.LENGTH_SHORT).show();
                                            fixturePercentages.clear();
                                            list.clear();

                                            int latestDay = waterList.get(0).getDay();
                                            int latestMonth = waterList.get(0).getMonth();
                                            int latestYear = waterList.get(0).getYear();
                                            int latestHour = waterList.get(0).getHour();

                                            for (Water water: waterList) {
                                                if (water.getYear() > latestYear) {
                                                    latestYear = water.getYear();
                                                    if (water.getMonth() > latestMonth) {
                                                        latestMonth = water.getMonth();
                                                        if (water.getDay() > latestDay) {
                                                            latestDay = water.getDay();
                                                            if (water.getHour() > latestHour) {
                                                                latestHour = water.getHour();
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            for (int i = 0; i < waterList.size(); i++) {
                                                if (waterList.get(i).getFixture().equals(fixtureOpt.get(position))) {
                                                    list.add(waterList.get(i));
                                                }
                                            }
                                            if (list.isEmpty() == true) { waterBinding.fixtureData.removeAllViews(); }

                                            String[] months = {"January", "February", "March", "April",
                                                    "May", "June", "July", "August", "September",
                                                    "October", "November", "December"};
                                            double totalVol[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                            double fixtureVol[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                            boolean leak = false;
                                            double fixturePercent[] = new double[fixtureVol.length];
                                            int monthChecked = 1;

                                            if (!list.isEmpty()) {
                                                while (monthChecked <= latestMonth) {
                                                    for (Water water: waterList) {
                                                        if (water.getMonth() == monthChecked) {
                                                            totalVol[monthChecked - 1] += water.getVolumeFlow();
                                                        }
                                                    }
                                                    for (Water water: list) {
                                                        if (water.getMonth() == monthChecked) {
                                                            fixtureVol[monthChecked - 1] += water.getVolumeFlow();
                                                        }
                                                        if (water.isLeak()) {
                                                            leak = true;
                                                        }
                                                    }
                                                    fixturePercent[monthChecked - 1] = fixtureVol[monthChecked - 1] / totalVol[monthChecked - 1] * 100;
                                                    fixturePercentages.add(new FixturePercentage(months[monthChecked - 1], leak, fixtureVol[monthChecked - 1], fixturePercent[monthChecked - 1]));
                                                    monthChecked++;
                                                }
                                                // Initialize the adapter
                                                mAdapterF = new FixturesRecyclerViewAdapter(fixturePercentages);

                                                // Set the layout manager
                                                waterBinding.setWaterManagerF(llmF.get());

                                                // Set the adapter
                                                waterBinding.setPercentAdapter(mAdapterF);
                                            } else {
                                                waterBinding.fixtureData.removeAllViews();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    FragmentManager fm = getChildFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(waterBinding.fixtureData.getId(), Fixtures.newInstance("", ""));

                                } else if (arraySpinner[position] == R.string.tab_text_3) {
                                    constraintLayoutFixtures.setVisibility(View.INVISIBLE);
                                    constraintLayoutInterval.setVisibility(View.VISIBLE);
                                    constraintLayoutWaterBill.setVisibility(View.INVISIBLE);

                                    final Spinner intervalSpin = waterBinding.enterInterval;
                                    list = new ArrayList<Water>();
                                    list.add(waterList.get(0));
                                    bills = new ArrayList<Bill>();
                                    conserve = (MainActivity) getActivity();

                                    // interval options put into an arrayList of strings
                                    final String[] intervalOpt = getResources().getStringArray(R.array.interval);

                                    // Add spinner and array adapter for time interval
                                    final ArrayAdapter<CharSequence> adapterT = ArrayAdapter.createFromResource(view.getContext(),
                                            R.array.interval,
                                            android.R.layout.simple_spinner_item);
                                    adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    waterBinding.setIntervalAdapter(adapterT);

                                    // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
                                    intervalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                            list.clear();
                                            bills.clear();
                                            waterBinding.waterDataInterval.removeAllViews();
                                            int latestDay = 1;
                                            int latestMonth = 1;
                                            int latestYear = 1970;
                                            int latestHour = 0;

                                            for (Water water: waterList) {
                                                if (water.getYear() >= latestYear) {
                                                    latestYear = water.getYear();
                                                    if (water.getMonth() >= latestMonth) {
                                                        latestMonth = water.getMonth();
                                                        if (water.getDay() >= latestDay) {
                                                            latestDay = water.getDay();
                                                            if (water.getHour() >= latestHour) {
                                                                latestHour = water.getHour();
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            boolean leak = false;
                                            double vol = 0;

                                            if (position == 1) { // Option is "hourly"
                                                for (int i = 0; i < waterList.size(); i++) {
                                                    if (waterList.get(i).getDay() == latestDay && waterList.get(i).getHour() <= latestHour) {
                                                        list.add(waterList.get(i));
                                                        mAdapterT.notifyDataSetChanged();
                                                    }
                                                }
                                                int hourChecked = 0;
                                                while (hourChecked <= latestHour) {
                                                    for (int i = 0; i < list.size(); i++) {
                                                        if (list.get(i).getHour() == hourChecked) {
                                                            if (list.get(i).isLeak()) {
                                                                leak = true;
                                                            }
                                                            vol += list.get(i).getVolumeFlow();
                                                            list.get(i).setMinute(0);
                                                        }
                                                    }
                                                    bills.add(new Bill(hourChecked + ":00", leak, vol, 0));
                                                    mAdapterT.notifyDataSetChanged();
                                                    vol = 0;
                                                    leak = false;
                                                    hourChecked++;
                                                }
                                            } else if (position == 2) { // Option is "daily"
                                                for (int i = 0; i < waterList.size(); i++) {
                                                    if (waterList.get(i).getMonth() == latestMonth) {
                                                        list.add(waterList.get(i));
                                                    }
                                                }
                                                int dayChecked = 1;
                                                while (dayChecked <= latestDay) {
                                                    for (int i = 0; i < list.size(); i++) {
                                                        if (list.get(i).getDay() == dayChecked) {
                                                            if (list.get(i).isLeak()) {
                                                                leak = true;
                                                            }
                                                            vol += list.get(i).getVolumeFlow();
                                                        }
                                                    }
                                                    bills.add(new Bill(dayChecked + "/" + latestMonth + "/" + latestYear, leak, vol, 0));
                                                    mAdapterT.notifyDataSetChanged();
                                                    vol = 0;
                                                    leak = false;
                                                    dayChecked++;
                                                }
                                            } else if (position == 3) { // Option is "monthly"
                                                for (int i = 0; i < waterList.size(); i++) {
                                                    if (waterList.get(i).getYear() == latestYear) {
                                                        list.add(waterList.get(i));
                                                    }
                                                }
                                                String[] months = {"January", "February", "March", "April,",
                                                        "May", "June", "July", "August", "September",
                                                        "October", "November", "December"};
                                                int monthChecked = 1;
                                                while (monthChecked <= latestMonth) {
                                                    for (int i = 0; i < list.size(); i++) {
                                                        if (list.get(i).getMonth() == monthChecked) {
                                                            if (list.get(i).isLeak()) {
                                                                leak = true;
                                                            }
                                                            vol += list.get(i).getVolumeFlow();
                                                        }
                                                    }
                                                    bills.add(new Bill(months[monthChecked - 1] + " " + latestYear, leak, vol, 0));
                                                    mAdapterT.notifyDataSetChanged();
                                                    vol = 0;
                                                    leak = false;
                                                    monthChecked++;
                                                }
                                            } else { // Option is yearly
                                                for (int i = latestYear - 5; i <= latestYear; i++) {
                                                    for (Water water: waterList) {
                                                        if (water.getYear() == i) {
                                                            list.add(water);
                                                        }
                                                    }
                                                }
                                                for (int i = latestYear - 5; i <= latestYear; i++) {
                                                    for (int j = 0; j < list.size(); j++) {
                                                        if (list.get(j).getYear() == i) {
                                                            if (list.get(j).isLeak()) {
                                                                leak = true;
                                                            }
                                                            vol += list.get(j).getVolumeFlow();
                                                        }
                                                    }
                                                    bills.add(new Bill("" + i, leak, vol, 0));
                                                    mAdapterT.notifyDataSetChanged();
                                                    vol = 0;
                                                    leak = false;
                                                }
                                            }
                                            Toast.makeText(v.getContext(), intervalOpt[position], Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    // Initialize the adapter
                                    mAdapterT = new IntervalRecyclerViewAdapter(bills);

                                    // Set the layout manager
                                    waterBinding.setWaterManagerT(llmT.get());

                                    // Set the adapter
                                    waterBinding.setWaterAdapterT(mAdapterT);

                                    FragmentManager fm = getChildFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(waterBinding.waterDataInterval.getId(), Fixtures.newInstance("", ""));
                                } else if (arraySpinner[position] == R.string.tab_text_4) {
                                    constraintLayoutFixtures.setVisibility(View.INVISIBLE);
                                    constraintLayoutInterval.setVisibility(View.INVISIBLE);
                                    constraintLayoutWaterBill.setVisibility(View.VISIBLE);

                                    // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
                                    final Spinner waterBillSpin = waterBinding.enterRate;
                                    list = new ArrayList<Water>();
                                    bills = new ArrayList<Bill>();
                                    conserve = (MainActivity) getActivity();

                                    // Water Bill rates put into an arraylist of strings
                                    final String[] billRates = getResources().getStringArray(R.array.bill_rates);

                                    // Add spinner and array adapter for fixture
                                    final ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(view.getContext(),
                                            R.array.bill_rates,
                                            android.R.layout.simple_spinner_item);
                                    adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    waterBinding.setBillAdapter(adapterB);

                                    // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
                                    waterBillSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                            bills.clear();
                                            waterBinding.billData.removeAllViews();

                                            String[] months = {"January", "February", "March", "April,",
                                                    "May", "June", "July", "August", "September",
                                                    "October", "November", "December"};

                                            int latestDay = waterList.get(0).getDay();
                                            int latestMonth = waterList.get(0).getMonth();
                                            int latestYear = waterList.get(0).getYear();
                                            int latestHour = waterList.get(0).getHour();
                                            double latestBill = 0;
                                            Water latestWater = waterList.get(0);

                                            for (Water water : waterList) {
                                                if (water.getYear() > latestYear) {
                                                    latestYear = water.getYear();
                                                    if (water.getMonth() > latestMonth) {
                                                        latestMonth = water.getMonth();
                                                        if (water.getDay() > latestDay) {
                                                            latestDay = water.getDay();
                                                            if (water.getHour() > latestHour) {
                                                                latestHour = water.getHour();
                                                                latestWater = water;
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            if (!waterList.isEmpty()) {
                                                if (position == 1) { // Water Bill is uniform rate.
                                                    latestBill = latestWater.getVolumeFlow() * 0.264 * 0.00295;
                                                    bills.add(new Bill(latestWater.toDateString(), latestWater.isLeak(), latestWater.getVolumeFlow(), latestBill));
                                                } else if (position == 2) { // Increasing rates
                                                    double vol = latestWater.getVolumeFlow();
                                                    if (vol * 0.264 < 1000) {
                                                        latestBill = latestWater.getVolumeFlow() * 0.264 * 0.00295;
                                                    } else if (vol * 0.264 < 3000) {
                                                        latestBill = 1000 * 0.00295 + (latestWater.getVolumeFlow() * 0.264 - 1000) * 0.00295 * 2;
                                                    } else if (vol * 0.264 < 5000) {
                                                        latestBill = 1000 * 0.00295 + 2000 * 0.00295 * 2 + (latestWater.getVolumeFlow() * 0.264 - 3000) * 0.00295 * 3;
                                                    } else {
                                                        latestBill = 1000 * 0.00295 + 2000 * 0.00295 * 2 + 2000 * 0.00295 * 3 + (latestWater.getVolumeFlow() * 0.264 - 5000) * 0.00295 * 4;
                                                    }
                                                    bills.add(new Bill(latestWater.toDateString(), latestWater.isLeak(), latestWater.getVolumeFlow(), latestBill));
                                                } else if (position == 3) { // Decreasing rate

                                                    double vol = latestWater.getVolumeFlow();
                                                    if (vol * 0.264 < 1000) {
                                                        latestBill = latestWater.getVolumeFlow() * 0.264 * 0.00295 * 4;
                                                    } else if (vol * 0.264 < 3000) {
                                                        latestBill = 1000 * 0.00295 * 4 + (latestWater.getVolumeFlow() * 0.264 - 1000) * 0.00295 * 3;
                                                    } else if (vol * 0.264 < 5000) {
                                                        latestBill = 1000 * 0.00295 * 4 + 2000 * 0.00295 * 3 + (latestWater.getVolumeFlow() * 0.264 - 3000) * 0.00295 * 2;
                                                    } else {
                                                        latestBill = 1000 * 0.00295 * 4 + 2000 * 0.00295 * 3 + 2000 * 0.00295 * 2 + (latestWater.getVolumeFlow() * 0.264 - 5000) * 0.00295;
                                                    }
                                                    bills.add(new Bill(latestWater.toDateString(), latestWater.isLeak(), latestWater.getVolumeFlow(), latestBill));
                                                }
                                            } else {
                                                waterBinding.billData.removeAllViews();
                                            }

                                            // Initialize the adapter
                                            mAdapterB = new WaterBillRecyclerViewAdapter(bills);

                                            // Set the layout manager
                                            waterBinding.setWaterManagerWB(llmWB.get());

                                            // Set the adapter
                                            waterBinding.setWaterBillAdapter(mAdapterB);

                                            FragmentManager fm = getChildFragmentManager();
                                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                            fragmentTransaction.replace(waterBinding.billData.getId(), Fixtures.newInstance("", ""));

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                } else {
                                    constraintLayoutFixtures.setVisibility(View.INVISIBLE);
                                    constraintLayoutInterval.setVisibility(View.INVISIBLE);
                                    constraintLayoutWaterBill.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    public List<Water> addWater(Water water) {
        waterList.add(water);
        return waterList;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

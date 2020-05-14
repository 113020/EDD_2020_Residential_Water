package com.example.edd_2020_residential_water.interval;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.fixtures.FixturesRecyclerViewAdapter;
import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.models.Track;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.databinding.FragmentIntervalBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Interval#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Interval extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String fixture;
    private String interval;

    private OnFragmentInteractionListener mListener;
    private List<Water> waterList; // Original list of Water objects
    private List<Water> listByFixture; // To be modified by the fixture spinner's onSelectedItemListener()
    private List<Water> listByInterval; // To be modified by the interval spinner's onSelectedItemListener()
    private List<Track> tracks; // List of track objects for the interval data binding layout

    private FragmentIntervalBinding waterBinding;
    private FixturesRecyclerViewAdapter mAdapterF;
    private IntervalRecyclerViewAdapter mAdapterT;
    private RecyclerView fluid;
    private MainActivity conserve;

    private SharedViewModel svm;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment overall.
     */
    // TODO: Rename and change types and number of parameters
    public static Interval newInstance(String param1, String param2) {
        Interval fragment = new Interval();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_interval, container, false);
        final View view = waterBinding.getRoot();

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
        final Spinner fixtureSpin = waterBinding.enterFixture;
        final Spinner intervalSpin = waterBinding.enterInterval;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        listByFixture = new ArrayList<Water>();
        listByInterval = new ArrayList<Water>();
        tracks = new ArrayList<Track>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.waterDataInterval;

        // Fixture options put into an arrayList of strings
        final String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        // Add spinner and array adapter for fixture
        final ArrayAdapter<CharSequence> adapterF = ArrayAdapter.createFromResource(view.getContext(),
                R.array.fixture,
                android.R.layout.simple_spinner_item);
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setFixtureAdapter(adapterF);

        // interval options put into an arrayList of strings
        final String[] intervalOpt = getResources().getStringArray(R.array.interval);

        // Add spinner and array adapter for time interval
        final ArrayAdapter<CharSequence> adapterT = ArrayAdapter.createFromResource(view.getContext(),
                R.array.interval,
                android.R.layout.simple_spinner_item);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setIntervalAdapter(adapterT);

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Create the listener for the fixture spinner: responsible for getting the list based on the fixture option
        fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                listByFixture.clear();
                tracks.clear();
                if (position == 0) {
                    intervalSpin.setVisibility(View.INVISIBLE);
                } else {
                    intervalSpin.setVisibility(View.VISIBLE);
                }

                if (position != fixtureOpt.length - 1) {
                    for (int i = 0; i < waterList.size(); i++) {
                        if (waterList.get(i).getFixture().equals(fixtureOpt[position])) {
                            listByFixture.add(waterList.get(i));
                            mAdapterF.notifyDataSetChanged();
                        }
                    }
                } else {
                    listByFixture.addAll(waterList);
                    mAdapterF.notifyDataSetChanged();
                }

                if (listByFixture.isEmpty() == true) {
                    fluid.removeAllViews();
                }
                Toast.makeText(v.getContext(), fixtureOpt[position], Toast.LENGTH_SHORT).show();
                setFixture(fixtureOpt[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
        intervalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                listByInterval.clear();
                tracks.clear();
                fluid.removeAllViews();
                if (!listByFixture.isEmpty()) {
                    int secondExtent, second, minute;
                    int hour = listByFixture.get(listByFixture.size() - 1).getHour();
                    int day = listByFixture.get(listByFixture.size() - 1).getDay();
                    int month = listByFixture.get(listByFixture.size() - 1).getMonth();
                    int year = listByFixture.get(listByFixture.size() - 1).getYear();
                    boolean leak = false;
                    double vol = 0;

                    if (position == 1) { // Option is "hourly"
                        for (int i = 0; i < listByFixture.size(); i++) {
                            if (listByFixture.get(i).getDay() == day && listByFixture.get(i).getHour() >= 0 && listByFixture.get(i).getHour() <= hour) {
                                secondExtent = listByFixture.get(i).getSecondExtent();
                                second = listByFixture.get(i).getSecond();
                                minute = listByFixture.get(i).getMinute();
                                hour = listByFixture.get(i).getHour();
                                if (secondExtent + second >= 60) {
                                    if (secondExtent + minute * 60 + second >= 3600) {
                                        // 1st entry
                                        second = listByFixture.get(i).getSecond() + secondExtent - 60;
                                        listByFixture.get(i).setSecondExtent(secondExtent);
                                        listByFixture.get(i).setVolumeFlow(listByFixture.get(i).getFlowRate() * (secondExtent - second));
                                        listByInterval.add(listByFixture.get(i));
                                        mAdapterF.notifyDataSetChanged();
                                        // 2nd entry
                                        secondExtent = second;
                                        hour++;
                                        minute = 0;
                                        second = 0;
                                        listByInterval.add(new Water(listByFixture.get(i).getDay(), listByFixture.get(i).getMonth(), listByFixture.get(i).getYear(),
                                                hour, minute, second, listByFixture.get(i).getFixture(), listByFixture.get(i).getFlowRate(),
                                                second, listByFixture.get(i).isLeak(), listByFixture.get(i).getFlowRate() * secondExtent,
                                                Math.round((listByFixture.get(i).getVolumeFlow() * 0.01116696697) * 100.0) / 100.0
                                                ));
                                    } else {
                                        minute++;
                                        secondExtent = Math.abs(secondExtent + second - 60);
                                        listByFixture.get(i).setSecondExtent(secondExtent);
                                        listByFixture.get(i).setMinute(minute);
                                        listByFixture.get(i).setVolumeFlow(listByFixture.get(i).getFlowRate() * secondExtent);
                                        listByInterval.add(listByFixture.get(i));
                                        mAdapterF.notifyDataSetChanged();
                                    }
                                } else {
                                    listByInterval.add(listByFixture.get(i));
                                    mAdapterF.notifyDataSetChanged();
                                }
                            }
                        }
                        int hourChecked = 0;
                        while (hourChecked <= hour) {
                            for (int i = 0; i < listByInterval.size(); i++) {
                                if (listByInterval.get(i).getHour() == hourChecked) {
                                    if (listByInterval.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += listByInterval.get(i).getVolumeFlow();
                                    listByInterval.get(i).setMinute(0);
                                }
                            }
                            tracks.add(new Track(hourChecked + ":00", leak, vol, 0));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            leak = false;
                            hourChecked++;
                        }
                    } else if (position == 2) { // Option is "daily"
                        for (int i = 0; i < listByFixture.size(); i++) {
                            if (listByFixture.get(i).getMonth() == month) {
                                listByInterval.add(listByFixture.get(i));
                            }
                        }
                        int dayChecked = 1;
                        while (dayChecked <= listByInterval.get(listByInterval.size() - 1).getDay()) {
                            for (int i = 0; i < listByInterval.size(); i++) {
                                if (listByInterval.get(i).getDay() == dayChecked) {
                                    if (listByInterval.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += listByInterval.get(i).getVolumeFlow();
                                }
                            }
                            tracks.add(new Track(dayChecked + "/" +
                                    listByInterval.get(listByInterval.size() - 1).getMonth() + "/" +
                                    listByInterval.get(listByInterval.size() - 1).getYear(), leak, vol, 0));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            leak = false;
                            dayChecked++;
                        }
                    } else if (position == 3) { // Option is "monthly"
                        for (int i = 0; i < listByFixture.size(); i++) {
                            if (listByFixture.get(i).getYear() == year) {
                                listByInterval.add(listByFixture.get(i));
                            }
                        }
                        String[] months = {"January", "February", "March", "April,",
                                "May", "June", "July", "August", "September",
                                "October", "November", "December"};
                        int monthChecked = 1;
                        while (monthChecked <= listByInterval.get(listByInterval.size() - 1).getMonth()) {
                            for (int i = 0; i < listByInterval.size(); i++) {
                                if (listByInterval.get(i).getMonth() == monthChecked) {
                                    if (listByInterval.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += listByInterval.get(i).getVolumeFlow();
                                }
                            }
                            tracks.add(new Track(months[monthChecked - 1] + " " + listByInterval.get(listByInterval.size() - 1).getYear(), leak, vol, 0));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            leak = false;
                            monthChecked++;
                        }
                    } else { // Option is yearly
                        for (int i = year - 19; i <= year; i++) {
                            for (Water water: listByFixture) {
                                if (water.getYear() == i) {
                                    listByInterval.add(water);
                                }
                            }
                        }
                        for (int i = year - 19; i <= year; i++) {
                            for (int j = 0; j < listByInterval.size(); j++) {
                                if (listByInterval.get(j).getYear() == i) {
                                    if (listByInterval.get(j).isLeak()) {
                                        leak = true;
                                    }
                                    vol += listByInterval.get(j).getVolumeFlow();
                                }
                            }
                            tracks.add(new Track("" + i, leak, vol, 0));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            leak = false;
                        }
                    }
                    Toast.makeText(v.getContext(), intervalOpt[position], Toast.LENGTH_SHORT).show();
                } else {
                    fluid.removeAllViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the adapter
        mAdapterF = new FixturesRecyclerViewAdapter(listByFixture);
        mAdapterT = new IntervalRecyclerViewAdapter(tracks);

        // Set the layout manager
        waterBinding.setWaterManager(wllm);

        // Set the adapter
        waterBinding.setWaterAdapter(mAdapterT);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));

        // Inflate the layout for this fragment
        return view;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
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
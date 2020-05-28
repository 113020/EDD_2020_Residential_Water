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
import com.example.edd_2020_residential_water.models.Bill;
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
    private List<Water> listByInterval; // To be modified by the interval spinner's onSelectedItemListener()
    private List<Bill> bills; // List of track objects for the interval data binding layout

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
        final Spinner intervalSpin = waterBinding.enterInterval;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        listByInterval = new ArrayList<Water>();
        bills = new ArrayList<Bill>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.waterDataInterval;

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

        // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
        intervalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                listByInterval.clear();
                bills.clear();
                fluid.removeAllViews();
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
                            listByInterval.add(waterList.get(i));
                            mAdapterT.notifyDataSetChanged();
                        }
                    }
                    int hourChecked = 0;
                    while (hourChecked <= latestHour) {
                        for (int i = 0; i < listByInterval.size(); i++) {
                            if (listByInterval.get(i).getHour() == hourChecked) {
                                if (listByInterval.get(i).isLeak()) {
                                    leak = true;
                                }
                                vol += listByInterval.get(i).getVolumeFlow();
                                listByInterval.get(i).setMinute(0);
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
                            listByInterval.add(waterList.get(i));
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
                        bills.add(new Bill(dayChecked + "/" +
                                listByInterval.get(listByInterval.size() - 1).getMonth() + "/" +
                                listByInterval.get(listByInterval.size() - 1).getYear(), leak, vol, 0));
                        mAdapterT.notifyDataSetChanged();
                        vol = 0;
                        leak = false;
                        dayChecked++;
                    }
                } else if (position == 3) { // Option is "monthly"
                    for (int i = 0; i < waterList.size(); i++) {
                        if (waterList.get(i).getYear() == latestYear) {
                            listByInterval.add(waterList.get(i));
                        }
                    }
                    String[] months = {"January", "February", "March", "April,",
                            "May", "June", "July", "August", "September",
                            "October", "November", "December"};
                    int monthChecked = 1;
                    while (monthChecked <= latestMonth) {
                        for (int i = 0; i < listByInterval.size(); i++) {
                            if (listByInterval.get(i).getMonth() == monthChecked) {
                                if (listByInterval.get(i).isLeak()) {
                                    leak = true;
                                }
                                vol += listByInterval.get(i).getVolumeFlow();
                            }
                        }
                        bills.add(new Bill(months[monthChecked - 1] + " " + listByInterval.get(listByInterval.size() - 1).getYear(), leak, vol, 0));
                        mAdapterT.notifyDataSetChanged();
                        vol = 0;
                        leak = false;
                        monthChecked++;
                    }
                } else { // Option is yearly
                    for (int i = latestYear - 19; i <= latestYear; i++) {
                        for (Water water: waterList) {
                            if (water.getYear() == i) {
                                listByInterval.add(water);
                            }
                        }
                    }
                    for (int i = latestYear - 19; i <= latestYear; i++) {
                        for (int j = 0; j < listByInterval.size(); j++) {
                            if (listByInterval.get(j).getYear() == i) {
                                if (listByInterval.get(j).isLeak()) {
                                    leak = true;
                                }
                                vol += listByInterval.get(j).getVolumeFlow();
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
        waterBinding.setWaterManager(wllm);

        // Set the adapter
        waterBinding.setWaterAdapterT(mAdapterT);

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
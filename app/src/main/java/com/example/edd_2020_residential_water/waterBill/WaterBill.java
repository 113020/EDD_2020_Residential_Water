package com.example.edd_2020_residential_water.waterBill;

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
import com.example.edd_2020_residential_water.interval.IntervalRecyclerViewAdapter;
import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.models.Track;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.databinding.FragmentWaterBillBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterBill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterBill extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WaterBill.OnFragmentInteractionListener mListener;
    private List<Water> waterList; // Original list of Water objects
    private List<Water> list; // List of water objects
    private List<Track> tracks; // List of track objects for the interval data binding layout

    private FragmentWaterBillBinding waterBinding;
    private FixturesRecyclerViewAdapter mAdapterF;
    private IntervalRecyclerViewAdapter mAdapterT;
    private WaterBillRecyclerViewAdapter mAdapterB;
    private RecyclerView fluid;
    private MainActivity conserve;

    private SharedViewModel svm;

    public WaterBill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment waterBill.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterBill newInstance(String param1, String param2) {
        WaterBill fragment = new WaterBill();
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
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_water_bill, container, false);
        final View view = waterBinding.getRoot();

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
        final Spinner waterBillSpin = waterBinding.enterWaterBill;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        waterList = new ArrayList<Water>();
        list = new ArrayList<Water>();
        tracks = new ArrayList<Track>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.waterBillData;

        // Water Bill rates put into an arraylist of strings
        final String[] billRates = getResources().getStringArray(R.array.water_bill_method);

        // Add spinner and array adapter for fixture
        final ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(view.getContext(),
                R.array.water_bill_method,
                android.R.layout.simple_spinner_item);
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setFixtureAdapter(adapterB);

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
        waterBillSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                list.clear();
                tracks.clear();
                fluid.removeAllViews();

                for (Water water: waterList) {
                    if (water.getYear() == waterList.get(waterList.size() - 1).getYear()) {
                        list.add(water);
                    }
                }

                if (!list.isEmpty()) {
                    int month = list.get(list.size() - 1).getMonth();
                    int year = list.get(list.size() - 1).getYear();
                    boolean leak = false;
                    double vol = 0;

                    String[] months = {"January", "February", "March", "April,",
                            "May", "June", "July", "August", "September",
                            "October", "November", "December"};

                    if (position == 1) { // Water Bill is uniform rate.
                        int monthChecked = 1;
                        while (monthChecked <= month) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getMonth() == monthChecked) {
                                    if (list.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += list.get(i).getVolumeFlow();
                                }
                            }
                            tracks.add(new Track(months[monthChecked - 1] + " " + list.get(list.size() - 1).getYear(),
                                    leak, vol, (Math.round(vol * 0.01116696697))));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            leak = false;
//                            tracks.get(monthChecked - 1).setTotalVolume(0);
                            monthChecked++;
                        }
                    } else if (position == 2) { // Increasing rate.
                        int monthChecked = 1;
                        double bill = 0;
                        while (monthChecked <= month) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getMonth() == monthChecked) {
                                    if (list.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += list.get(i).getVolumeFlow();
                                    if (vol * 0.264 > 1000) {
                                        bill += 2 * (Math.round(((vol) - 1000) * 0.01116696697));
                                    }
                                    if (vol * 0.264 > 2000) {
                                        bill += 3 * (Math.round(((vol) - 2000) * 0.01116696697));
                                    }
                                    if (vol * 0.264 < 1000) {
                                        bill += (Math.round((vol) * 0.01116696697));
                                    }
                                }
                            }
                            tracks.add(new Track(months[monthChecked - 1] + " " + list.get(list.size() - 1).getYear(),
                                    leak, vol, bill));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            bill = 0;
                            leak = false;
                            monthChecked++;
                        }
                    } else if (position == 3) { // Decreasing rate
                        int monthChecked = 1;
                        double bill = 0;
                        while (monthChecked <= month) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getMonth() == monthChecked) {
                                    if (list.get(i).isLeak()) {
                                        leak = true;
                                    }
                                    vol += list.get(i).getVolumeFlow();
                                    if (vol * 0.264 > 1000) {
                                        bill += 2 * (Math.round(((vol) - 1000) * 0.01116696697));
                                    } else if (vol * 0.264 > 2000) {
                                        bill += (Math.round(((vol) - 2000) * 0.01116696697));
                                    } else {
                                        bill += 3 * (Math.round(((vol) - 1000) * 0.01116696697));
                                    }
                                }
                            }
                            tracks.add(new Track(months[monthChecked - 1] + " " + list.get(list.size() - 1).getYear(),
                                    leak, vol, bill));
                            mAdapterT.notifyDataSetChanged();
                            vol = 0;
                            bill = 0;
                            leak = false;
                            monthChecked++;
                        }
                    }

                    Toast.makeText(v.getContext(), billRates[position], Toast.LENGTH_SHORT).show();
                } else {
                    fluid.removeAllViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the adapter
        mAdapterT = new IntervalRecyclerViewAdapter(tracks);
        mAdapterB = new WaterBillRecyclerViewAdapter(tracks);

        // Set the layout manager
        waterBinding.setWaterManager(wllm);

        // Set the adapter
        waterBinding.setWaterAdapter(mAdapterB);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));

        // Inflate the layout for this fragment
        return view;
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
package com.example.edd_2020_residential_water;

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

import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;
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

    private OnFragmentInteractionListener mListener;
    private List<Water> waterList; // Original list of Water objects
    private List<Water> listByFixture; // To be modified by the fixture spinner's onSelectedItemListener()
    private List<Water> listByInterval; // To be modified by the interval spinner's onSelectedItemListener()

    private FragmentIntervalBinding waterBinding;
    private FixturesRecyclerViewAdapter mAdapterF;
    private IntervalRecyclerViewAdapter mAdapterT;
    private RecyclerView fluid;
    private MainActivity conserve;

    private SharedViewModel svm;

    // Variables for Interval model
    private String interval;
    private String fixture;
    private int dateOrTime;
    private double leakPercent;
    private double totalVolume;
    private double totalBill;

    public Interval() {
        // Required empty public constructor
    }

    public Interval(String interval, String fixture, int dateOrTime, double leakPercent, double totalVolume, double totalBill) {
        this.interval = interval;
        this.fixture = fixture;
        this.dateOrTime = dateOrTime;
        this.leakPercent = leakPercent;
        this.totalVolume = totalVolume;
        this.totalBill = totalBill;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public int getDateOrTime() {
        return dateOrTime;
    }

    public void setDateOrTime(int dateOrTime) {
        this.dateOrTime = dateOrTime;
    }

    public double getLeakPercent() {
        return leakPercent;
    }

    public void setLeakPercent(double leakPercent) {
        this.leakPercent = leakPercent;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Overall.
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
        listByInterval = new ArrayList<Water>()
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

        // Interval options put into an arrayList of strings
        final String[] intervalOpt = getResources().getStringArray(R.array.interval);
        int dateOrTime;

        // Add spinner and array adapter for time interval
        final ArrayAdapter<CharSequence> adapterT = ArrayAdapter.createFromResource(view.getContext(),
                R.array.interval,
                android.R.layout.simple_spinner_item);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setIntervalAdapter(adapterT);

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Create the listener for the fixture spinner: responsible for getting the list based on the option
        fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    intervalSpin.setVisibility(View.INVISIBLE);
                } else {
                    intervalSpin.setVisibility(View.VISIBLE);
                    setFixture(fixtureOpt[position]);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        intervalSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                if (position != 0 ) {
                    setInterval(intervalOpt[position]);
                    if (position == 1) { // Option is "hourly"
                        setDateOrTime(listByFixture.get(listByFixture.size() - 1).getDay());
                    } else if (position == 2) { // Option is "daily"
                        setDateOrTime(list.get(list.size() - 1).getMonth());
                    } else if (position == 3) {
                        setDateOrTime(list.get(list.size() - 1).getYear());
                    } else {}

                    int leak = 0;
                    double vol = 0;
                    double bill = 0;

                    for (Water water: list) {
                        if ()
                    }
                    setLeakPercent(leak / list.size());
                    setTotalVolume(vol);
                    setTotalBill(bill);
                }
                Toast.makeText(v.getContext(), intervalOpt[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the adapter
        mAdapterF = new FixturesRecyclerViewAdapter(list);
        mAdapterT = new IntervalRecyclerViewAdapter();

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
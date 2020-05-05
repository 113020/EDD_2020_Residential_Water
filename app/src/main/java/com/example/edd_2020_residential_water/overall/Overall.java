package com.example.edd_2020_residential_water.overall;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.models.FixturePercentage;
import com.example.edd_2020_residential_water.fixtures.FixturesRecyclerViewAdapter;
import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.databinding.FragmentOverallBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Overall.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Overall#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overall extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<Water> waterList; // Original list of Water objects
    private List<Water> list;
    private List<FixturePercentage> fixturePercentages;

    private FragmentOverallBinding waterBinding;
    private FixturesRecyclerViewAdapter mAdapterW;
    private OverallRecyclerViewAdapter mAdapterO;
    private RecyclerView fluid;
    private MainActivity conserve;

    private SharedViewModel svm;

    public Overall() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment com.example.edd_2020_residential_water.Fixtures.Fixtures.
     */
    // TODO: Rename and change types and number of parameters
    public static Overall newInstance(String param1, String param2) {
        Overall fragment = new Overall();
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
        // Bind the layout with water binding to allow data display
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_overall, container, false);
        final View view = waterBinding.getRoot();

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        list = new ArrayList<Water>();
        fixturePercentages = new ArrayList<FixturePercentage>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.overallData;

        // Fixture options put into an arrayList of strings
        final String[] fixtureOpt = getResources().getStringArray(R.array.fixture);
        final String[] allFixtures = new String[getResources().getStringArray(R.array.fixture).length - 2];
        int j = 0;
        for (int i = 0; i < fixtureOpt.length; i++) {
            if (i > 0 && i < fixtureOpt.length - 1) {
                allFixtures[j] = fixtureOpt[i];
                j++;
            } else {

            }
            int l = 0;
        }
        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        for (Water water: waterList) {
            if (water.getYear() == waterList.get(waterList.size() - 1).getYear()) {
                list.add(water);
            }
        }

        String[] months = {"January", "February", "March", "April,",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};
        double fixtureVol[] = new double[allFixtures.length];
        double fixturePercent[] = new double[fixtureVol.length];
        double totalVol[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int monthChecked = 1;

        while (monthChecked <= list.get(list.size() - 1).getMonth()) {
            for (Water water: list) {
                if (water.getMonth() == monthChecked) {
                    totalVol[monthChecked - 1] += water.getVolumeFlow();
                }
            }
            monthChecked++;
        }
        monthChecked = 1;

        while (monthChecked <= list.get(list.size() - 1).getMonth()) {

            for (int i = 0; i < allFixtures.length; i++) {
                for (Water water: list) {
                    if (water.getFixture().equals(allFixtures[i])) {
                        if (water.getMonth() == monthChecked) {
                            fixtureVol[i] += water.getVolumeFlow();
                        }
                    }
                }
                fixturePercent[i] = fixtureVol[i] / totalVol[monthChecked - 1] * 100;
                fixturePercentages.add(new FixturePercentage(months[monthChecked - 1], allFixtures[i], fixtureVol[i], fixturePercent[i]));
                fixturePercent[i] = 0;
                fixtureVol[i] = 0;
            }
            totalVol[monthChecked - 1] = 0;
            monthChecked++;
        }

        /*while (monthChecked <= list.get(list.size() - 1).getMonth()) {
            for (int s = 0;s < allFixtures.length; s++) {
                for (int i = 0; i < list.size(); i++) {
                    if (allFixtures[s] == "Shower 1") {

                    }
                    fixtureVol += list.get(i).getVolumeFlow();
                }
                fixturePercent = fixtureVol / totalVol;
                fixturePercentages.add(new FixturePercentage(months[monthChecked - 1], allFixtures[s], fixtureVol, fixturePercent));
                if (k < fixtureOpt.length) {
                    totalVol = 0;
                }
                k++;
            }

            monthChecked++;
        }*/



        // Create the listener for the spinner: responsible for getting the list based on the option
        /*fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(v.getContext(), fixtureOpt[position], Toast.LENGTH_SHORT).show();
                conserve.clearWaterList(list);

                *//*list.addAll(getFixtureList(fixtureOpt[position], waterList));
                adapterW.notifyDataSetChanged();*//*

                if (position != fixtureOpt.length - 1) {
                    for (int i = 0; i < waterList.size(); i++) {
                        if (waterList.get(i).getFixture().equals(fixtureOpt[position])) {
                            list.add(waterList.get(i));
                            madapterW.notifyDataSetChanged();
                        }
                    }
                } else {
                    list.addAll(waterList);
                    adapterW.notifyDataSetChanged();
                }

                if (list.isEmpty() == true) {
                    fluid.removeAllViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        // Initialize the adapter
        mAdapterW = new FixturesRecyclerViewAdapter(list);
        mAdapterO = new OverallRecyclerViewAdapter(fixturePercentages);

        // Set the layout manager
        waterBinding.setPercentManager(wllm);

        // Set the adapter
        waterBinding.setPercentAdapter(mAdapterO);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fluid.getId(), Overall.newInstance("", ""));

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
        void onFragmentInteraction(Uri uri);
    }
}

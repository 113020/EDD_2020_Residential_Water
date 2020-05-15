package com.example.edd_2020_residential_water.fixtures;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
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

import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.models.FixturePercentage;
import com.example.edd_2020_residential_water.models.Water;
import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fixtures.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fixtures#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fixtures extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<Water> waterList; // Original list of Water objects
    private List<Water> list; // To be modified by the onSelectedItemListener()
    private List<FixturePercentage> fixturePercentages;

    private FragmentFixturesBinding waterBinding;
    private FixturesRecyclerViewAdapter mAdapterF;
    private RecyclerView fluid;
    private MainActivity conserve;

    private SharedViewModel svm;

    public Fixtures() {
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
    public static Fixtures newInstance(String param1, String param2) {
        Fixtures fragment = new Fixtures();
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
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fixtures, container, false);
        final View view = waterBinding.getRoot();

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
        final Spinner fixtureSpin = waterBinding.enterFixture;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        waterList = new ArrayList<Water>();
        list = new ArrayList<Water>();
        fixturePercentages = new ArrayList<FixturePercentage>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.fixtureData;

        fluid.removeAllViews();

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Fixture options put into an arrayList of strings
        final String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        // Add spinner and array adapter
        final ArrayAdapter<CharSequence> adapterF = ArrayAdapter.createFromResource(view.getContext(),
                R.array.fixture,
                android.R.layout.simple_spinner_item);
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setFixtureAdapter(adapterF);

        // Create the listener for the spinner: responsible for getting the list based on the option
        fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(v.getContext(), fixtureOpt[position], Toast.LENGTH_SHORT).show();
                conserve.clearWaterList(list);
                fixturePercentages.clear();

                for (int i = 0; i < waterList.size(); i++) {
                    if (waterList.get(i).getFixture().equals(fixtureOpt[position])) {
                        list.add(waterList.get(i));
                    }
                }
                if (list.isEmpty() == true) { fluid.removeAllViews(); }

                String[] months = {"January", "February", "March", "April",
                        "May", "June", "July", "August", "September",
                        "October", "November", "December"};
                double totalVol[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                double fixtureVol[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                double fixturePercent[] = new double[fixtureVol.length];
                int monthChecked = 0;

                if (!list.isEmpty()) {
                    while (monthChecked <= list.get(list.size() - 1).getMonth()) {
                        for (Water water: waterList) {
                            if (water.getMonth() == monthChecked) {
                                totalVol[monthChecked] += water.getVolumeFlow();
                            }
                        }
                        for (Water water: list) {
                            if (water.getMonth() == monthChecked) {
                                fixtureVol[monthChecked] += water.getVolumeFlow();
                            }
                        }
                        fixturePercent[monthChecked] = fixtureVol[monthChecked] / totalVol[monthChecked] * 100;
                        fixturePercentages.add(new FixturePercentage(months[monthChecked], fixtureOpt[position], fixtureVol[monthChecked], fixturePercent[monthChecked]));
                        monthChecked++;
                    }
                    // Initialize the adapter
                    mAdapterF = new FixturesRecyclerViewAdapter(fixturePercentages);

                    // Set the layout manager
                    waterBinding.setWaterManager(wllm);

                    // Set the adapter
                    waterBinding.setPercentAdapter(mAdapterF);
                } else {
                    fluid.removeAllViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        void onFragmentInteraction(Uri uri);
    }
}

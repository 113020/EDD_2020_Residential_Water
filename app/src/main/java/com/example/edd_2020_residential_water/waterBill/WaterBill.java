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
import android.widget.TextView;
import android.widget.Toast;

import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.models.Bill;
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
    private MainActivity conserve;
    private List<Water> waterList;
    private List<Water> list;
    private List<Bill> bills;

    private FragmentWaterBillBinding waterBinding;
    private WaterBillRecyclerViewAdapter mAdapterB;
    private RecyclerView fluid;
    private TextView textVolume;
    private TextView textWaterBill;

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

//        textVolume = waterBinding.txtVolume;
//        textWaterBill = waterBinding.txtWaterBill;

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
//        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
//        Button displayWaterBill = waterBinding.btnDisplayBill;
//
//
//        fluid = waterBinding.waterBillData;
//        bills = new ArrayList<Bill>();
//
//        String bill = "";
//
//        if (!waterBinding.enterPlace.getText().toString().isEmpty()) {
//            if ()
//        }
//        fluid.setText("Water Bill: $" + bill);
//        // Initialize the adapter
//        mAdapterB = new WaterBillRecyclerViewAdapter(bills);
//
//        // Set the layout manager
//        waterBinding.setWaterManager(wllm);
//
//        // Set the adapter
//        waterBinding.setWaterAdapter(mAdapterB);

        // Declare needed objects and bind them to the corresponding elements in the layout: Cleaner version of findViewById(R.id....)
        final Spinner waterBillSpin = waterBinding.enterRate;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        waterList = new ArrayList<Water>();
        list = new ArrayList<Water>();
        bills = new ArrayList<Bill>();
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.billData;

        // Water Bill rates put into an arraylist of strings
        final String[] billRates = getResources().getStringArray(R.array.bill_rates);

        // Add spinner and array adapter for fixture
        final ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(view.getContext(),
                R.array.bill_rates,
                android.R.layout.simple_spinner_item);
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setBillAdapter(adapterB);

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Create the listener for the interval spinner: responsible for displaying the data based on the chosen time interval
        waterBillSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                bills.clear();
                fluid.removeAllViews();

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
                    }  else if (position == 2) { // Increasing rates
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
                    fluid.removeAllViews();
                }

                // Initialize the adapter
                mAdapterB = new WaterBillRecyclerViewAdapter(bills);

                // Set the layout manager
                waterBinding.setWaterManager(wllm);

                // Set the adapter
                waterBinding.setWaterBillAdapter(mAdapterB);

                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
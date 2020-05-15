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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.fixtures.FixturesRecyclerViewAdapter;
import com.example.edd_2020_residential_water.interval.IntervalRecyclerViewAdapter;
import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.models.Bill;
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
    private List<Bill> bills; // List of track objects for the interval data binding layout

    private FragmentWaterBillBinding waterBinding;
    private WaterBillRecyclerViewAdapter mAdapterB;
    private TextView fluid;

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
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        Button displayWaterBill = waterBinding.btnDisplayBill;


        fluid = waterBinding.waterBillData;
        bills = new ArrayList<Bill>();

//        bills.add(new Bill(place, bill));

        displayWaterBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bills.clear();

                String place = "";
                String bill = "";

                if (!waterBinding.enterPlace.getText().toString().isEmpty()) {
                    place = waterBinding.enterPlace.getText().toString();
                }
                if (!waterBinding.enterWaterBill.getText().toString().isEmpty()) {
                    bill = waterBinding.enterWaterBill.getText().toString();
                }
                fluid.setText("" + place + ": $" + bill);
            }
        });

//        // Initialize the adapter
//        mAdapterB = new WaterBillRecyclerViewAdapter(bills);
//
//        // Set the layout manager
//        waterBinding.setWaterManager(wllm);
//
//        // Set the adapter
//        waterBinding.setWaterAdapter(mAdapterB);



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
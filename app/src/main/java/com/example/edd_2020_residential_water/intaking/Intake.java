package com.example.edd_2020_residential_water.intaking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.edd_2020_residential_water.models.Water;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    List<Water> waterList;
    List<Integer> day, month, year, hour, min, sec;
    List<String> fixtures;
    List<Double> volume;

    private FragmentIntakeBinding waterBinding;
    private IntakeRecyclerViewAdapter mAdapterI;
    private RecyclerView fluid;
    private MainActivity conserve;
    private View view;

    private SharedViewModel svm;

    private OnFragmentInteractionListener mListener;

    private LinearLayoutManager wllm;

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

        wllm = new LinearLayoutManager(view.getContext());
        conserve = (MainActivity) getActivity();

        fluid = waterBinding.waterDataIntake;
        fluid.setLayoutManager(wllm);

        final Water water = new Water();
        waterList = new ArrayList<Water>();
        waterList.clear();

        //reading data from firebase database
        FirebaseDatabase.getInstance().getReference().child("Master Shower").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
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

                    mAdapterI = new IntakeRecyclerViewAdapter(waterList);

                    // Set the layout manager
                    waterBinding.setWaterManager(new LinearLayoutManager(view.getContext()));

                    // Set the adapter
                    waterBinding.setWaterAdapter(mAdapterI);

                    FragmentManager fm = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    /*public void initWaters(List<Water> water) {
        final Water water1 = new Water();
        //reading data from firebase database
        FirebaseDatabase.getInstance().getReference().child("Master Shower").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    water.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    water.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
                    water.setYear(Calendar.getInstance().get(Calendar.YEAR));
                    water.setHour(Calendar.getInstance().get(Calendar.HOUR));
                    water.setMinute(Calendar.getInstance().get(Calendar.MINUTE));
                    water.setSecond(Calendar.getInstance().get(Calendar.SECOND));
                    water.setFixture(dataSnapshot.getKey().toString() != null ? dataSnapshot.getKey().toString() : "");
                    water.setFlowRateL(dataSnapshot.child("flowL").getValue() != null ? Double.parseDouble(dataSnapshot.child("flowL").getValue().toString()) : 0);
                    water.setFlowRateML(dataSnapshot.child("flowML").getValue() != null ? Double.parseDouble(dataSnapshot.child("flowML").getValue().toString()) : 0);
                    water.setSecondExtent(0);
                    water.setLeak(dataSnapshot.child("Leaking").getValue() != null ? Boolean.parseBoolean(dataSnapshot.child("Leaking").getValue().toString()) : false);
                    water.setVolumeFlow(dataSnapshot.child("totalVolume").getValue() != null ? Double.parseDouble(dataSnapshot.child("totalVolume").getValue().toString()) : 0);
                    waterList.add(water);

                    mAdapterI = new IntakeRecyclerViewAdapter(waterList);

                    // Set the layout manager
                    waterBinding.setWaterManager(new LinearLayoutManager(view.getContext()));

                    // Set the adapter
                    waterBinding.setWaterAdapter(mAdapterI);

                    FragmentManager fm = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

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

package com.example.edd_2020_residential_water.intaking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.edd_2020_residential_water.MainActivity;
import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.SharedViewModel;
import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;
import com.example.edd_2020_residential_water.databinding.FragmentIntakeBinding;
import com.example.edd_2020_residential_water.fixtures.Fixtures;
import com.example.edd_2020_residential_water.fixtures.FixturesRecyclerViewAdapter;
import com.example.edd_2020_residential_water.models.Water;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

    private SharedViewModel svm;

    private OnFragmentInteractionListener mListener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_intake, container, false);
        final View view = waterBinding.getRoot();

        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());

        fluid = waterBinding.waterDataIntake;

        final Calendar cal = Calendar.getInstance();
        waterList = new ArrayList<Water>();
        waterList.clear();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mGetReference = mDatabase.getReference();

        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fluid.removeAllViews();
                    waterList.add(new Water(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR),
                            cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),"Shower",
                            dataSnapshot.child("Fixture").child("flowL").getValue() != null ? (double) ((long) dataSnapshot.child("Fixture").child("flowL").getValue()) : 0,
                            0, true,
                            dataSnapshot.child("Fixture").child("totalVolume").getValue() != null ? (double) ((long) dataSnapshot.child("Fixture").child("totalVolume").getValue()) : 0));
//                    mAdapterI.notifyDataSetChanged();

                    // Initialize the adapter
                    mAdapterI = new IntakeRecyclerViewAdapter(waterList);

                    // Set the layout manager
                    waterBinding.setWaterManager(wllm);

                    // Set the adapter
                    waterBinding.setWaterAdapter(mAdapterI);
//                    Toast.makeText(view.getContext(), "" + (long) dataSnapshot.child("Fixture").child("totalVolume").getValue(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Data does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));
//        waterList.add(mWater2);
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

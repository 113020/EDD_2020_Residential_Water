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
    private String mParam1;
    private String mParam2;

    Water mWater2;
    List<Water> waterList;
    List<Integer> day;
    List<Integer> month;
    List<Integer> year;
    List<Integer> hour;
    List<Integer> min;
    List<Integer> sec;
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

//        final Query query = ;
        mGetReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fluid.removeAllViews();
//                    long flowL = (long) dataSnapshot.child("Fixture").child("flowL").getValue();
//                    long vol = (long) dataSnapshot.child("Fixture").child("totalVolume").getValue();
                    waterList.add(new Water(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR),
                            cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
                            "Shower", (double) ((long) dataSnapshot.child("Fixture").child("flowL").getValue()),
                            0, true, (double) ((long) dataSnapshot.child("Fixture").child("totalVolume").getValue()), 0));

                    /*for (int i = 0; i < waterList.size(); i++) {
                        if (waterList.get(i) == null) {
                            waterList.remove(i);
                        }
                    }*/
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

//        mGetReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fluid.removeAllViews();
//
////                day.add(cal.get(Calendar.DAY_OF_MONTH));
////                month.add(cal.get(Calendar.MONTH));
////                year.add(cal.get(Calendar.YEAR));
////                hour.add(cal.get(Calendar.HOUR));
////                min.add(cal.get(Calendar.MINUTE));
////                sec.add(cal.get(Calendar.SECOND));
////                fixtures.add(dataSnapshot.child("Fixture").getValue().toString());
////                volume.add((double) vol);
//                long flowL = (long) dataSnapshot.child("Fixture").child("flowL").getValue();
//                long vol = (long) dataSnapshot.child("Fixture").child("totalVolume").getValue();
//                waterList.add(new Water(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR),
//                        cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
//                        "Shower", (double) flowL, 0, true, (double) vol, 0));
//
//                Toast.makeText(view.getContext(), "" + vol, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(view.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

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

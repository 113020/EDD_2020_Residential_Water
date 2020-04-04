package com.example.edd_2020_residential_water;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;

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
    private List<Water> waterList;

    private FragmentFixturesBinding waterBinding;
    private MyWaterRecyclerViewAdapter adapterW;
    private RecyclerView fluid;
    private MainActivity conserve;

    private WaterViewModel waterViewModel;
    private SharedViewModel svm;

    public Fixtures() { /* Required empty public constructor */ }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fixtures.
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
        final Button fixtureClear = waterBinding.btnClear;
        final LinearLayoutManager wllm = new LinearLayoutManager(view.getContext());
        conserve = (MainActivity) getActivity();
        fluid = waterBinding.waterData;

        // Listener attached to the button to clear the old recyclerView displays: good for updating the display once data is filtered
        fixtureClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conserve.clearWaterList(waterList);
                fluid.removeAllViews();
            }
        });

        // Fixture options put into an arrayList of strings
        final String[] fixtureOpt = getResources().getStringArray(R.array.fixture);

        // Add spinner and array adapter
        final ArrayAdapter<CharSequence> adapterF = ArrayAdapter.createFromResource(view.getContext(),
                R.array.fixture,
                android.R.layout.simple_spinner_item);
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterBinding.setFixtureAdapter(adapterF);

        // Get the list of water data and send that data to the adapter
        waterList = conserve.initWaters();

        // Create the listener for the spinner: responsible for getting the list based on the option
        fixtureSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(v.getContext(), fixtureOpt[position], Toast.LENGTH_LONG).show();

                waterList = getFixtureList(fixtureOpt[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the adapter
        adapterW = new MyWaterRecyclerViewAdapter(waterList);

        // Set the layout manager
        waterBinding.setWaterManager(wllm);

        // Set the adapter
        waterBinding.setWaterAdapter(adapterW);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fluid.getId(), Fixtures.newInstance("", ""));

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Allow the user to search for water use entries for a specified fixture in the dropdown
     * @param option: Fixture option chosen by the user
     * @return List of data that only concerns the specified fixture
     */
    public List<Water> getFixtureList(String option) {
        List<Water> data = mListener.getByFixture(option);
        if (mListener != null) {
            int i = data.size();
            if (data.isEmpty()) {
                Toast.makeText(getContext(), "No Water Uses Found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), i + " Water Uses Found", Toast.LENGTH_SHORT).show();
            }
            conserve.setWaterList(data);
            adapterW.notifyDataSetChanged();

            svm = new ViewModelProvider(this).get(SharedViewModel.class);
            svm.select(data);
        }
        return data;
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
        List<Water> getByFixture(String fixture);
        List<Water> getAllSplashes();
    }
}

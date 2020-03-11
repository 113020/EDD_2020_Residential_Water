package com.example.edd_2020_residential_water;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
        final View view = inflater.inflate(R.layout.fragment_fixtures, container, false);

        final Spinner chooseFixture = view.findViewById(R.id.enterFixture);
        final Button searchFixture = view.findViewById(R.id.btnSearch);

        final MainActivity conserve = (MainActivity) getActivity();

        final List<Splash> sampleWaterList = conserve.initWaters();
        final String[] fixtures = getResources().getStringArray(R.array.fixture);

        // Initializing an ArrayAdapter. Also important for custom text size, color, font, etc.through "spinner_fixture"
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.fixture, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        chooseFixture.setAdapter(adapter);

        // This adapter is for changing the text color, font, or size
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.fixture, R.layout.spinner_fixture);

        // Apply this adapter to the spinner
        chooseFixture.setAdapter(adapter1);


        chooseFixture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "" + item, Toast.LENGTH_LONG).show();

                // Showing selected spinner item
                if (item != fixtures[0]) {
                    onOptionSelected(item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set up the listener for the search button (when pressed, what will the button do)
        searchFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseFixture.getSelectedItem().toString().equals(fixtures[0]))
                Toast.makeText(view.getContext(), "Choose a fixture", Toast.LENGTH_SHORT).show();
            }
        });

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.data, Fixtures.newInstance("",""));

        // Inflate the layout for this fragment
        return view;
    }

    public void onOptionSelected(String fixture) {
        if (mListener != null) {
            List<Splash> data = mListener.getByFixture(fixture);
            WaterListFragment frag = (WaterListFragment) getChildFragmentManager().findFragmentById(R.id.data);
            if (data.isEmpty()) {
                Toast.makeText(getContext(), "No Sets Found", Toast.LENGTH_LONG).show();
            }
            frag.setWaterList(data);

            svm = new ViewModelProvider(this).get(SharedViewModel.class);
            svm.select(data);
        }
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
        List<Splash> getByFixture(String fixture);

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

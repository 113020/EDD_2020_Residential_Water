package com.example.edd_2020_residential_water;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;
import com.example.edd_2020_residential_water.databinding.FragmentFixturesListBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FixturesList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FixturesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FixturesList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<Water> waterList = new ArrayList<Water>();
    private FragmentFixturesListBinding waterBinding;
    private MyWaterRecyclerViewAdapter adapterW;

    private WaterViewModel waterViewModel;
    private SharedViewModel svm;

    public FixturesList() { /* Required empty public constructor */ }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fixtures.
     */
    // TODO: Rename and change types and number of parameters
    public static FixturesList newInstance(String param1, String param2) {
        FixturesList fragment = new FixturesList();
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

    public void setWaterList(List<Water> wl, MyWaterRecyclerViewAdapter adapt) {
        adapterW = adapt;
        waterList.clear();
        waterList.addAll(wl);
        adapterW.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Bind the layout with water binding to allow data display
        waterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fixtures_list, container, false);
        final View view = waterBinding.getRoot();

//        adapterW = new MyWaterRecyclerViewAdapter(waterList);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            // Bind the recyclerView to the corresponding view in the layout: Cleaner version of findViewById(R.id.water_data)
            final RecyclerView fluid = waterBinding.waterData;
            fluid.setLayoutManager(new LinearLayoutManager(context));
            fluid.setAdapter(adapterW);
        }

        // Inflate the layout for this fragment
        return view;
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
    }
}

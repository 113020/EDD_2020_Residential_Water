package com.example.edd_2020_residential_water;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterListFragment extends Fragment {
    private static final String EXTRA_REPLY = "com.example.android.waterlistsql.REPLY";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnFragmentInteractionListener mListener;
    private List<Splash> waterList = new ArrayList<Splash>();

    MyWaterRecyclerViewAdapter adapterW;
    private WaterViewModel waterViewModel;
    private SharedViewModel svm;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WaterListFragment() { /* Required empty public constructor */ }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WaterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterListFragment newInstance() {
        WaterListFragment fragment = new WaterListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setWaterList(List<Splash> wl) {
        waterList.clear();
        waterList.addAll(wl);
        adapterW.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_list, container, false);

        final RecyclerView fluid;
        adapterW = new MyWaterRecyclerViewAdapter(waterList, mListener);

        // Set the adapter
        if (view instanceof RecyclerView) {
            fluid = (RecyclerView) view;
            fluid.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
        List<Splash> getByFixture(String fixture);
    }
}

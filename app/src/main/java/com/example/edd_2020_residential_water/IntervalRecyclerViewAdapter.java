package com.example.edd_2020_residential_water;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IntervalRecyclerViewAdapter extends RecyclerView.Adapter<IntervalRecyclerViewAdapter.WaterViewHolder> {
    private List<Water> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        public WaterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

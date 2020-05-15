package com.example.edd_2020_residential_water.fixtures;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.models.FixturePercentage;
import com.example.edd_2020_residential_water.databinding.FixturesTableBinding;

import java.util.ArrayList;
import java.util.List;

public class FixturesRecyclerViewAdapter extends RecyclerView.Adapter<FixturesRecyclerViewAdapter.WaterViewHolder> {
    private List<FixturePercentage> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public FixturesRecyclerViewAdapter(List<FixturePercentage> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FixturesRecyclerViewAdapter.WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FixturesTableBinding fixturesTableBinding = FixturesTableBinding.inflate(layoutInflater, parent, false);
        return new FixturesRecyclerViewAdapter.WaterViewHolder(fixturesTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesRecyclerViewAdapter.WaterViewHolder holder, int position) {
        FixturePercentage p = mValues.get(position);
        holder.bind(p);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        private FixturesTableBinding binding;

        public WaterViewHolder(@NonNull FixturesTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FixturePercentage fixturePercentage) {
            binding.setVariable(BR.fixtureP, fixturePercentage);
            binding.executePendingBindings();
        }
    }
}

package com.example.edd_2020_residential_water.overall;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.models.FixturePercentage;
import com.example.edd_2020_residential_water.databinding.OverallTableBinding;

import java.util.List;

public class OverallRecyclerViewAdapter extends RecyclerView.Adapter<OverallRecyclerViewAdapter.WaterViewHolder> {
    private List<FixturePercentage> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public OverallRecyclerViewAdapter(List<FixturePercentage> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OverallTableBinding overallTableBinding = OverallTableBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(overallTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        FixturePercentage p = mValues.get(position);
        holder.bind(p);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        private OverallTableBinding binding;

        public WaterViewHolder(@NonNull OverallTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FixturePercentage fixturePercentage) {
            binding.setVariable(BR.fixtureP, fixturePercentage);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

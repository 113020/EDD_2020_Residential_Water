package com.example.edd_2020_residential_water;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.databinding.FixturesTableBinding;

import java.util.List;

public class FixturesRecyclerViewAdapter extends RecyclerView.Adapter<FixturesRecyclerViewAdapter.WaterViewHolder> {
    private List<Water> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public FixturesRecyclerViewAdapter(List<Water> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FixturesTableBinding fixturesTableBinding = FixturesTableBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(fixturesTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        Water w = mValues.get(position);
        holder.bind(w);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public static class WaterViewHolder extends RecyclerView.ViewHolder {
        private FixturesTableBinding binding;

        public WaterViewHolder(FixturesTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Water water) {
            binding.setVariable(BR.water, water);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

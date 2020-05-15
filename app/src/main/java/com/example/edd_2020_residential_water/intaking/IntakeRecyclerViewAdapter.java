package com.example.edd_2020_residential_water.intaking;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.databinding.IntakeTableBinding;
import com.example.edd_2020_residential_water.models.Water;

import java.util.List;

public class IntakeRecyclerViewAdapter extends RecyclerView.Adapter<IntakeRecyclerViewAdapter.WaterViewHolder> {
    private List<Water> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public IntakeRecyclerViewAdapter(List<Water> water) {
        mValues = water;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IntakeTableBinding intakeTableBinding = IntakeTableBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(intakeTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        Water w = mValues.get(position);
        holder.bind(w);
    }

    @Override
    public int getItemCount() { return mValues != null ? 1 : 0; }

    public static class WaterViewHolder extends RecyclerView.ViewHolder {
        private IntakeTableBinding binding;

        public WaterViewHolder(IntakeTableBinding binding) {
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

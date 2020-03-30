package com.example.edd_2020_residential_water;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.databinding.ItemWaterBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that makes a call to the
 * specified {@link Fixtures.OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWaterRecyclerViewAdapter extends RecyclerView.Adapter<MyWaterRecyclerViewAdapter.WaterViewHolder> {

    private List<Water> mValues;

    public MyWaterRecyclerViewAdapter(List<Water> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWaterBinding itemWaterBinding = ItemWaterBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(itemWaterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        Water w = mValues.get(position);
        holder.bind(w);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public static class WaterViewHolder extends RecyclerView.ViewHolder {
        private ItemWaterBinding binding;

        public WaterViewHolder(ItemWaterBinding binding) {
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
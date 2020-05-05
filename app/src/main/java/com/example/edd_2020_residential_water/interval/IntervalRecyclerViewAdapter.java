package com.example.edd_2020_residential_water.interval;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.models.Track;
import com.example.edd_2020_residential_water.databinding.IntervalTableBinding;

import java.util.List;

public class IntervalRecyclerViewAdapter extends RecyclerView.Adapter<IntervalRecyclerViewAdapter.WaterViewHolder> {
    private List<Track> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public IntervalRecyclerViewAdapter(List<Track> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IntervalTableBinding intervalTableBinding = IntervalTableBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(intervalTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        Track t = mValues.get(position);
        holder.bind(t);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        private IntervalTableBinding binding;

        public WaterViewHolder(@NonNull IntervalTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Track track) {
            binding.setVariable(BR.track, track);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

package com.example.edd_2020_residential_water.waterBill;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.models.Track;
import com.example.edd_2020_residential_water.databinding.BillCalcTableBinding;

import java.util.List;

public class WaterBillRecyclerViewAdapter extends RecyclerView.Adapter<WaterBillRecyclerViewAdapter.WaterViewHolder> {
    private List<Track> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public WaterBillRecyclerViewAdapter(List<Track> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BillCalcTableBinding billCalcTableBinding = BillCalcTableBinding.inflate(layoutInflater, parent, false);
        return new WaterViewHolder(billCalcTableBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {
        Track t = mValues.get(position);
        holder.bind(t);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        private BillCalcTableBinding binding;

        public WaterViewHolder(@NonNull BillCalcTableBinding binding) {
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

package com.example.edd_2020_residential_water.waterBill;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.BR;
import com.example.edd_2020_residential_water.databinding.BillCalcTableBinding;
import com.example.edd_2020_residential_water.models.Bill;

import java.util.List;

public class WaterBillRecyclerViewAdapter extends RecyclerView.Adapter<WaterBillRecyclerViewAdapter.WaterViewHolder> {
    private List<Bill> mValues;
    private AdapterView.OnItemClickListener mClickListener;

    public WaterBillRecyclerViewAdapter(List<Bill> items) {
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
        Bill b = mValues.get(position);
        holder.bind(b);
    }

    @Override
    public int getItemCount() { return mValues != null ? mValues.size() : 0; }

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        private BillCalcTableBinding binding;

        public WaterViewHolder(@NonNull BillCalcTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bill bill) {
            binding.setVariable(BR.bill, bill);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

package com.example.edd_2020_residential_water;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edd_2020_residential_water.databinding.FragmentFixturesBinding;

public class Holder extends RecyclerView.ViewHolder{
    public FragmentFixturesBinding binding;

    public Holder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}

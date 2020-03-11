package com.example.edd_2020_residential_water;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that makes a call to the
 * specified {@link WaterListFragment.OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWaterRecyclerViewAdapter extends RecyclerView.Adapter<MyWaterRecyclerViewAdapter.ViewHolder> {

    private final List<Splash> mValues;
    private final WaterListFragment.OnFragmentInteractionListener mListener;

    public MyWaterRecyclerViewAdapter(List<Splash> items, WaterListFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Splash w = mValues.get(position);

        holder.mDate.setText(w.getDate());
        holder.mTime.setText(w.getTime());
        holder.mFixture.setText(w.getFixture());
        holder.mSpeed.setText(Double.toString(w.getWaterSpeed()));
        holder.mExtent.setText(Double.toString(w.getWaterExtent()));
        holder.mInterval.setText(w.getTimeInterval());
        holder.mHourF.setText(Integer.toString(w.getHourlyFrequency()));
        holder.mDayF.setText(Integer.toString(w.getDailyFrequency()));
        holder.mWeekF.setText(Integer.toString(w.getWeeklyFrequency()));
        holder.mMonthF.setText(Integer.toString(w.getMonthlyFrequency()));
        holder.mYearF.setText(Integer.toString(w.getYearlyFrequency()));
        holder.mLeakF.setText(Integer.toString(w.getLeakFrequency()));
        holder.mBillMethod.setText(w.getBillMethod());
        holder.mTotalBill.setText(Double.toString(w.getWaterBill()));
    }

    @Override
    public int getItemCount() { return mValues.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDate, mTime, mFixture, mSpeed, mExtent, mInterval, mHourF, mDayF, mWeekF, mMonthF, mYearF, mLeakF, mBillMethod, mTotalBill;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDate = view.findViewById(R.id.water_date);
            mTime = view.findViewById(R.id.water_time);
            mFixture = view.findViewById(R.id.water_fixture);
            mSpeed = view.findViewById(R.id.water_speed);
            mExtent = view.findViewById(R.id.water_extent);
            mInterval = view.findViewById(R.id.water_time_interval);
            mHourF = view.findViewById(R.id.water_hourly_frequency);
            mDayF = view.findViewById(R.id.water_daily_frequency);
            mWeekF = view.findViewById(R.id.water_weekly_frequency);
            mMonthF = view.findViewById(R.id.water_monthly_frequency);
            mYearF = view.findViewById(R.id.water_yearly_frequency);
            mLeakF = view.findViewById(R.id.water_leak_frequency);
            mBillMethod = view.findViewById(R.id.water_bill_method);
            mTotalBill = view.findViewById(R.id.water_total_bill);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFixture.getText() + "'";
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}

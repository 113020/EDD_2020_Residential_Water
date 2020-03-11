package com.example.edd_2020_residential_water;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.edd_2020_residential_water.R;
import com.example.edd_2020_residential_water.Fixtures;
import com.example.edd_2020_residential_water.Overall;
import com.example.edd_2020_residential_water.Scan;
import com.example.edd_2020_residential_water.WaterBill;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
                return Scan.newInstance("","");
            case 1:
                return Fixtures.newInstance("","");
            case 2:
                return Overall.newInstance("","");
            default:
                return WaterBill.newInstance("","");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show all tabs.
        return TAB_TITLES.length;
    }
}
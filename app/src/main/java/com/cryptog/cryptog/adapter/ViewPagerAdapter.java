package com.cryptog.cryptog.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cryptog.cryptog.CountryOfVisit;
import com.cryptog.cryptog.MyFavorites;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] fragmentListTitles;
    public ViewPagerAdapter(FragmentManager fm, String[] fragmentListTitles) {
        super(fm);
        this.fragmentListTitles = fragmentListTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
            return new MyFavorites();
            case 1:
            return new CountryOfVisit();
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentListTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles[position];
    }
}

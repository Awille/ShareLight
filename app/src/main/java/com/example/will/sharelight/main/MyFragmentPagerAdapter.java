package com.example.will.sharelight.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"home", "square"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return MainActivity.getHomeFragment();
        } else if (i == 1) {
            return MainActivity.getSquareFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

}

package com.example.will.sharelight.main.square;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class RecommendFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = "RecommendFragAdapter";
    private RecommendPlayFragment currentFragment;

    public RecommendPlayFragment getCurrentFragment() {
        return currentFragment;
    }

    public RecommendFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        RecommendPlayFragment fragment = new RecommendPlayFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment = (RecommendPlayFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

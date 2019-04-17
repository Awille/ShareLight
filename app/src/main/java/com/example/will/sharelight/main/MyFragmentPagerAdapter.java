package com.example.will.sharelight.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.will.sharelight.main.square.RecommendPlayFragment;
import com.example.will.sharelight.main.square.SquareFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyFragmentPagerAdapter";

    private SquareFragment mSquareFragment;

    public SquareFragment getmSquareFragment() {
        return mSquareFragment;
    }

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
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (position == 1) {
            mSquareFragment = (SquareFragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 2;
    }

}

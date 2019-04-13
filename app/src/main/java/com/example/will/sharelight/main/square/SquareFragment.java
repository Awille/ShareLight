package com.example.will.sharelight.main.square;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.will.sharelight.R;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

public class SquareFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_square, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(getActivity()));
        //horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), false));

//        horizontalInfiniteCycleViewPager.setScrollDuration(400);
//        horizontalInfiniteCycleViewPager.setInterpolator(
//                AnimationUtils.loadInterpolator(getContext(), android.R.anim.overshoot_interpolator)
//        );
//        horizontalInfiniteCycleViewPager.setMediumScaled(false);
//        horizontalInfiniteCycleViewPager.setMaxPageScale(0.8F);
//        horizontalInfiniteCycleViewPager.setMinPageScale(0.5F);
//        horizontalInfiniteCycleViewPager.setCenterPageScaleOffset(30.0F);
//        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(5.0F);
//        horizontalInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener();

//        horizontalInfiniteCycleViewPager.setCurrentItem(
//                horizontalInfiniteCycleViewPager.getRealItem() + 1
//        );
    }
}

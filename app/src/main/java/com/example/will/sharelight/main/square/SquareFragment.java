package com.example.will.sharelight.main.square;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.will.protocol.CommonConstant;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.sharelight.palyer.GetProgressThread;
import com.example.will.sharelight.palyer.broadcast.PlayerBroadcast;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import static android.content.Context.BIND_AUTO_CREATE;

public class SquareFragment extends Fragment implements ViewPager.OnPageChangeListener, PlayerBroadcast.onBroadcastRecieveListener {

    private static final String TAG = "SquareFragment";

    private RecommendFragmentAdapter recommendFragmentAdapter;

    private VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager;

    private Handler handler = new Handler();

    private PlayerBroadcast playerBroadcast;


    public RecommendFragmentAdapter getRecommendFragmentAdapter() {
        return recommendFragmentAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_square, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        playerBroadcast = new PlayerBroadcast();
        playerBroadcast.setListener(this);
        getActivity().registerReceiver(playerBroadcast, new IntentFilter(
                CommonConstant.BroadcastName.MUSIC_PREPARED));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }



    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        verticalInfiniteCycleViewPager = view.findViewById(R.id.hicvp);
        recommendFragmentAdapter = new RecommendFragmentAdapter(getChildFragmentManager());
        verticalInfiniteCycleViewPager.setAdapter(recommendFragmentAdapter);
        verticalInfiniteCycleViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(playerBroadcast);
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    public boolean getIsPrepared() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int isPrepared = 0;
        try {
            data.writeInt(100);
            ((MainActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.MUSIC_PREPARED, data, reply, 0);
            isPrepared = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        if (isPrepared == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        recommendFragmentAdapter.getCurrentFragment().playRecommendSong();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onSongPrepared(boolean isSame, int duration) {
        recommendFragmentAdapter.getCurrentFragment().onSongPrepared(isSame, duration);
    }

    @Override
    public void onSongFinished(int index) {
        verticalInfiniteCycleViewPager.setCurrentItem(verticalInfiniteCycleViewPager.getRealItem() + 1);
    }
}

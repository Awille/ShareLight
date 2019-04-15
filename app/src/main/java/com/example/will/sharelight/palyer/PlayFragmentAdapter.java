package com.example.will.sharelight.palyer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.will.protocol.song.Song;

import java.util.List;

public class PlayFragmentAdapter extends FragmentPagerAdapter {

    private static final String TAG = "PlayFragmentAdapter";

    private PlayFragment mCurrentFragment;

    private static List<Song> songList;
    private static int currentIndex;
    private static boolean isPlay;

    public PlayFragment getmCurrentFragment() {
        return mCurrentFragment;
    }

    public PlayFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 必须先调用 warning
     * @param isPlay
     * @param songList
     * @param currentIndex
     */
    public static void initPlayFragmentAdapter(boolean isPlay, List<Song> songList,int currentIndex) {
        setIsPlay(isPlay);
        setSongList(songList);
        setCurrentIndex(currentIndex);
    }

    public static void setIsPlay(boolean isPlay) {
        PlayFragmentAdapter.isPlay = isPlay;
    }

    public static void setSongList(List<Song> songList) {
        PlayFragmentAdapter.songList = songList;
    }

    public static void setCurrentIndex(int currentIndex) {
        PlayFragmentAdapter.currentIndex = currentIndex;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (PlayFragment) object;
        super.setPrimaryItem(container, position, object);
    }



    @Override
    public Fragment getItem(int i) {
        PlayFragment playFragment = new PlayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CURRENT_SONG", JSON.toJSONString(songList.get(i % songList.size())));
        playFragment.setArguments(bundle);
        return playFragment;
    }

    @Override
    public int getCount() {
        //return PlayFragmentAdapter.songList.size();
        return Integer.MAX_VALUE;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

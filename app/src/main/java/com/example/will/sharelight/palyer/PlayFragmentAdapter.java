package com.example.will.sharelight.palyer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.will.protocol.song.Song;

import java.util.List;

public class PlayFragmentAdapter extends FragmentPagerAdapter {

    private static List<Song> songList;
    private static int currentIndex;
    private static boolean isPlay;

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
        PlayFragment.isPlay = isPlay;
    }

    public static void setSongList(List<Song> songList) {
        PlayFragment.songList = songList;
    }

    public static void setCurrentIndex(int currentIndex) {
        PlayFragment.currentIndex = currentIndex;
    }



    @Override
    public Fragment getItem(int i) {
        return new PlayFragment();
    }

    @Override
    public int getCount() {
        return PlayFragment.songList.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

package com.example.will.sharelight.main.square;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.will.protocol.song.Song;
import com.example.will.protocol.songlist.SongList;
import com.example.will.sharelight.R;

import java.util.List;

public class VerticalPagerAdapter extends PagerAdapter {

    private List<Song> songs;
    private Context context;

    public VerticalPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_select_layout, container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}

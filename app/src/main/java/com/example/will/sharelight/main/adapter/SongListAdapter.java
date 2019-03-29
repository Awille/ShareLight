package com.example.will.sharelight.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.songlist.SongList;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.utils.TextUtils;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListViewHolder> {

    private List<SongList> songLists;
    private Context context;

    public SongListAdapter(List<SongList> songLists, Context context) {
        this.songLists = songLists;
        this.context = context;
    }

    public List<SongList> getSongLists() {
        return songLists;
    }

    public void setSongLists(List<SongList> songLists) {
        this.songLists = songLists;
    }

    @NonNull
    @Override
    public SongListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new SongListViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.main_song_list_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongListViewHolder songListViewHolder, int position) {
        if (!TextUtils.isEmpty(songLists.get(position).getBasicInfo().getAvatarUrl())) {
            ImageLoader.build(context).bindBitmap(RetrofitMrg.baseUrl + songLists.get(position).getBasicInfo().getAvatarUrl(),
                    songListViewHolder.songListAvatar,
                    150, 150);
        } else {
            songListViewHolder.songListAvatar.setImageResource(R.drawable.image_loading);
        }
        songListViewHolder.songListName.setText(songLists.get(position).getBasicInfo().getName());
        songListViewHolder.songListSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).getSongListSettingDialog().showDialog(songLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return songLists.size();
    }
}

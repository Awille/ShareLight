package com.example.will.sharelight.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.utils.TextUtils;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private static final String TAG = "SongAdapter";

    private List<Song> songs;
    private Context context;

    public SongAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SongViewHolder(LayoutInflater.from(context).inflate(R.layout.main_song_item,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, int position) {
        if (!TextUtils.isEmpty(songs.get(position).getAvatarUrl())) {
            ImageLoader.build(context).bindBitmap(RetrofitMrg.baseUrl + songs.get(position).getAvatarUrl(),
                    songViewHolder.songAvatar,
                    150, 150);
        } else {
            songViewHolder.songAvatar.setImageResource(R.drawable.image_loading);
        }
        songViewHolder.songName.setText(songs.get(position).getName());
        String singer = songs.get(position).getSinger();
        String albumName = songs.get(position).getAlbumName();
        if (!TextUtils.isEmpty(singer) && !TextUtils.isEmpty(albumName)) {
            songViewHolder.singerAndAlbum.setText(singer + "-" + albumName);
        } else if (TextUtils.isEmpty(singer) && !TextUtils.isEmpty(albumName)) {
            songViewHolder.singerAndAlbum.setText(albumName);
        } else if (!TextUtils.isEmpty(singer) && TextUtils.isEmpty(albumName)) {
            songViewHolder.singerAndAlbum.setText(singer);
        } else {
            songViewHolder.singerAndAlbum.setText("");
        }

        if (!TextUtils.isEmpty(songs.get(position).getAuthorAccount())) {
            songViewHolder.athorName.setText(songs.get(position).getAuthorAccount());
        } else {
            songViewHolder.authorPannel.setVisibility(View.INVISIBLE);
        }

        songViewHolder.songSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "设置 " + position);
                ((MainActivity)context).getSongSettingDialog().showDialog(songs.get(position));
            }
        });
        songViewHolder.songPannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "点击歌曲 " + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}

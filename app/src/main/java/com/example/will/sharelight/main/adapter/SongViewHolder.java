package com.example.will.sharelight.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.sharelight.R;

public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "SongViewHolder";

    public ImageView songAvatar;
    public TextView songName;
    public TextView singerAndAlbum;
    public LinearLayout authorPannel;
    public TextView athorName;
    public LinearLayout songPannel;


    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View view) {
        songAvatar = view.findViewById(R.id.song_avatar);
        songName = view.findViewById(R.id.song_name);
        singerAndAlbum = view.findViewById(R.id.singer_album);
        authorPannel = view.findViewById(R.id.author_pannel);
        athorName = view.findViewById(R.id.author);
        songPannel = view.findViewById(R.id.song_pannel);
        songPannel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.song_pannel: {
                Log.e(TAG, "点击歌曲");
                break;
            }
        }
    }
}

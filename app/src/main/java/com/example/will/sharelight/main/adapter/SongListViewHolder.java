package com.example.will.sharelight.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.will.sharelight.R;

public class SongListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "SongListViewHolder";

    public ImageView songListAvatar;
    public TextView songListName;
    public ImageView songListSetting;

    public SongListViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        songListAvatar = itemView.findViewById(R.id.song_list_avatar);
        songListName = itemView.findViewById(R.id.song_list_name);
        songListSetting = itemView.findViewById(R.id.song_list_item_setting);
        songListSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.song_list_item_setting: {
                Log.e(TAG, "点击歌单设置");
                break;
            }
        }
    }
}

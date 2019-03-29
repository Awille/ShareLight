package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;

public class SongSettingDialog implements View.OnClickListener {
    private Dialog dialog;
    private Song song;
    private Context context;
    private TextView songName;
    private LinearLayout uploadAvatarPannel;
    private LinearLayout uploadResourcePannel;

    public static final int SELECT_SONG_RESOURCE = 5;

    public SongSettingDialog(Context context) {
        this.context = context;
    }

    //设置显示歌曲的数据信息
    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void showDialog(Song song) {
        this.song = song;
        if (dialog == null) {
            initDialog();
        }
        if (song != null) {
            songName.setText("歌曲: " + song.getName());
        }
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void dismissDialog() {
        if  (dialog != null) {
            dialog.dismiss();
        }
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_song_setting, null);
        dialog.setContentView(view);
        initView(view);

    }

    private void initView(View view) {
        songName = view.findViewById(R.id.song_name);
        uploadAvatarPannel = view.findViewById(R.id.upload_avatar_pannel);
        uploadResourcePannel = view.findViewById(R.id.upload_resource_pannel);
        uploadResourcePannel.setOnClickListener(this);
        uploadAvatarPannel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.upload_avatar_pannel: {
                ((MainActivity)context).getSongAvatarSelectChannelDialog().showDialog(song);
                dismissDialog();
                break;
            }
            case R.id.upload_resource_pannel: {
                clickUploadSongResource();
                break;
            }
        }
    }

    private void clickUploadSongResource() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        ((MainActivity)context).startActivityForResult(intent, SELECT_SONG_RESOURCE);
    }
}

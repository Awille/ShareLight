package com.example.will.sharelight.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.protocol.song.Song;
import com.example.will.protocol.songlist.SongList;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.sharelight.main.MainPresenterImpl;
import com.example.will.utils.loadingutils.LoadingUtils;

public class SongListSettingDialog implements View.OnClickListener {
    private Dialog dialog;
    private Context context;
    private SongList songList;
    private TextView songListName;
    private LinearLayout editInfoPannel;
    private LinearLayout uploadAvatarPannel;
    private LinearLayout deleteSongListPannel;
    private MainPresenterImpl mainPresenter;

    public SongListSettingDialog(Context context, MainPresenterImpl mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
    }


    public void showDialog(SongList songList) {
        this.songList = songList;
        if (dialog == null) {
            initDialog();
        }
        songListName.setText("歌单:" + songList.getBasicInfo().getName());
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_song_list_setting, null);
        dialog.setContentView(view);
        initView(view);
    }

    private void  initView(View view) {
        songListName = view.findViewById(R.id.song_list_name);
        editInfoPannel = view.findViewById(R.id.edit_info_pannel);
        uploadAvatarPannel = view.findViewById(R.id.upload_avatar_pannel);
        deleteSongListPannel = view.findViewById(R.id.delete_song_list_pannel);
        editInfoPannel.setOnClickListener(this);
        uploadAvatarPannel.setOnClickListener(this);
        deleteSongListPannel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.edit_info_pannel: {
                clickChangeSongListInfo();
                dismissDialog();
                break;
            }
            case R.id.upload_avatar_pannel: {
                ((MainActivity)context).getSongListAvatarSelectDialog().showDialog(songList);
                dismissDialog();
                break;
            }
            case R.id.delete_song_list_pannel: {
                clickDeleteSongList();
                break;
            }
        }
    }

    private void clickChangeSongListInfo() {
        EditSongListInfoDialog.build(context, "修改歌单名称", songList,
                new EditSongListInfoDialog.EditSongListActionListener() {
            @Override
            public void onCertainAction(String songListName, SongList songList) {
                LoadingUtils.getINSTANCE(context).showLoadingViewGhost();
                mainPresenter.changeSongListName(songListName, songList.getBasicInfo().getSongListId());
            }

            @Override
            public void onCancelAction() {
                //无动作
            }
        }).showDialog();
    }

    private void clickDeleteSongList() {
        WarnningDialog.build(context, "删除歌单", "确认删除本歌单?",
                new WarnningDialog.ActionListener() {
            @Override
            public void onCertain() {
                LoadingUtils.getINSTANCE(context).showLoadingViewGhost();
                mainPresenter.deleteSongList(songList.getBasicInfo().getSongListId());
                dismissDialog();
            }

            @Override
            public void onCancel() {
                //什么都不用做，dismissdialog在dialog里面已经做了
            }
        }).show();
    }
}

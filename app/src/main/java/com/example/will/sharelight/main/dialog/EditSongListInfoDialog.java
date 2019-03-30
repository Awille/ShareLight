package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.will.protocol.songlist.SongList;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.sharelight.main.MainContract;
import com.example.will.utils.TextUtils;
import com.example.will.utils.toast.ToastUtils;

public class EditSongListInfoDialog {
    private Context context;
    private Dialog dialog;
    private String titleStr;
    private SongList songList;


    private TextView title;
    private EditText songListNameEdit;
    private Button cancel;
    private Button certain;
    private EditSongListActionListener editSongListActionListener;

    public interface EditSongListActionListener {
        void onCertainAction(String songListName, SongList songList);
        void onCancelAction();
    }

    public static EditSongListInfoDialog build(Context context, String titleStr, SongList songList, EditSongListActionListener listener) {
        return new EditSongListInfoDialog(context, titleStr, songList, listener);
    }

    public EditSongListInfoDialog(Context context, String titleStr, SongList songList, EditSongListActionListener listener) {
        this.context = context;
        this.songList = songList;
        this.editSongListActionListener = listener;
        this.titleStr = titleStr;
    }
    //mode 1: 创建歌单  2、修改歌单名字
    public void showDialog() {
        initDialog(editSongListActionListener);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void initDialog(EditSongListActionListener listener) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_song_list, null);
        dialog.setContentView(view);
        initView(view);
        title.setText(titleStr);
        certain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songListName = songListNameEdit.getText().toString();
                if (!TextUtils.isEmpty(songListName)) {
                    listener.onCertainAction(songListName, songList);
                    dismissDialog();
                } else {
                    ToastUtils.showWarningToast(context, "歌单名不能为空", ToastUtils.LENGTH_LONG);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSongListActionListener.onCancelAction();
                dismissDialog();
            }
        });

    }

    private void initView(View view) {
        songListNameEdit = view.findViewById(R.id.edit_song_list_name);
        title = view.findViewById(R.id.title_txt);
        certain = view.findViewById(R.id.certain);
        cancel = view.findViewById(R.id.cancel);
    }



}

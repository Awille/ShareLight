package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.nfc.tech.NfcA;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainPresenterImpl;
import com.example.will.utils.TextUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;

public class AddSongDialogMrg implements View.OnClickListener {
    private static final String TAG = "AddSongDialogMrg";

    private Dialog dialog;
    private Context context;
    private MainPresenterImpl mainPresenter;

    private LinearLayout songBasicInfoPannel;
    private EditText songNameEdit;
    private EditText singerNameEdit;
    private EditText albumNameEdit;

    private Button certain;
    private Button cancel;

    public AddSongDialogMrg(Context context, MainPresenterImpl mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
    }

    public void showDialog() {
        if (dialog == null) {
            initDialog();
        }

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        double width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() * 0.72;
        lp.width = (int) width;
        dialog.getWindow().setAttributes(lp);
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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_upload_song, null);
        dialog.setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        songBasicInfoPannel = view.findViewById(R.id.song_baic_info);
        songNameEdit = view.findViewById(R.id.song_name_edit);
        singerNameEdit = view.findViewById(R.id.singer_name_edit);
        albumNameEdit = view.findViewById(R.id.album_name_edit);
        certain = view.findViewById(R.id.certain);
        cancel = view.findViewById(R.id.cancel);
        certain.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.certain : {
                Log.e(TAG, "点击了确认创建");
                clickActionBar();
                break;
            }
            case R.id.cancel : {
                Log.e(TAG, "点击了取消");
                dismissDialog();
                break;
            }
        }
    }

    private void clickActionBar() {
        String name = songNameEdit.getText().toString();
        String singer = singerNameEdit.getText().toString();
        String album = albumNameEdit.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(singer) || TextUtils.isEmpty(album)) {
            ToastUtils.showWarningToast(context, "信息不能为空", ToastUtils.LENGTH_SHORT);
        } else {
            Song song = new Song();
            song.setName(name);
            song.setSinger(singer);
            song.setAlbumName(album);
            song.setAuthor(MusicDataContext.getINSTANCE().getUser().getUserId());
            song.setAuthorAccount(MusicDataContext.getINSTANCE().getUser().getAccount());
            LoadingUtils.getINSTANCE(context).showLoadingViewGhost();
            mainPresenter.addSong(song);
        }
    }
}

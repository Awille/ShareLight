package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.will.protocol.UploadFile;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.song.request.UploadSongFileRequest;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainContract;
import com.example.will.sharelight.main.MainPresenterImpl;
import com.example.will.utils.ExecutorUtils;
import com.example.will.utils.FileUtils;
import com.example.will.utils.TextUtils;
import com.example.will.utils.loadingutils.LoadingUtils;

public class UploadSongResourceDialog implements View.OnClickListener {

    private static final String TAG = "UploadSongResource";

    private Dialog dialog;
    private Context context;
    private MainPresenterImpl mainPresenter;


    private String filePath;
    private Song song;

    private TextView fileName;
    private Button certain;
    private Button cacel;

    public UploadSongResourceDialog(Context context, MainPresenterImpl mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
    }


    public void showDialog(String filePath, Song song) {
        this.song = song;
        this.filePath = filePath;
        if (dialog == null) {
            initDialog();
        }
        fileName.setText(filePath);
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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_upload_song_resource, null);
        dialog.setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        fileName = view.findViewById(R.id.file_path);
        certain = view.findViewById(R.id.certain);
        cacel = view.findViewById(R.id.cancel);
        certain.setOnClickListener(this);
        cacel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.certain: {
                clickCertain();
                break;
            }
            case R.id.cancel: {
                dismissDialog();
                break;
            }
        }
    }

    private void clickCertain() {
       LoadingUtils.getINSTANCE(context).showLoadingViewNews();
        ExecutorUtils.runTask(new Runnable() {
            @Override
            public void run() {
                UploadFile uploadFile = new UploadFile();
                String format = "upload_resource" + filePath.substring(filePath.lastIndexOf("."));
                uploadFile.setFileName(format);
                uploadFile.setAccount(String.valueOf(song.getSongId()));
                uploadFile.setFileStr(FileUtils.fileToBase64(filePath).replaceAll("\r\n", ""));
                mainPresenter.changeSongResource(uploadFile);
            }
        });
    }
}

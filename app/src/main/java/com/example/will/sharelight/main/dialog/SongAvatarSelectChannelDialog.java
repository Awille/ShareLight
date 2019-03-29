package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.protocol.UploadFile;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.BuildConfig;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainPresenterImpl;
import com.example.will.utils.CircleImageView;
import com.example.will.utils.FileUtils;
import com.example.will.utils.loadingutils.LoadingUtils;

import java.io.File;

public class SongAvatarSelectChannelDialog implements View.OnClickListener {
    private static final String TAG = "SongAvatarSelectChannel";

    private Dialog dialog;
    private Context mContext;
    private LinearLayout selectPannel;
    private LinearLayout albumPannel;
    private LinearLayout cameraPannel;
    private ImageView close;

    private LinearLayout imgShowPannel;
    private CircleImageView showImg;
    private Button certain;
    private Button cancel;

    private MainPresenterImpl mainPresenter;

    private Uri imgUri;

    public static final int TAKE_PHOTO_SONG = 3;
    public static final int TAKE_ALBUM_SONG = 4;

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";
    private static final String PROVIDER_EXTERNAL_PATH = "/externalPath/";

    private Song song;

    private Bitmap currentBitmap;

    public SongAvatarSelectChannelDialog(Context context, MainPresenterImpl mainPresenter) {
        this.mContext = context;
        this.mainPresenter = mainPresenter;
    }

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
        imgShowPannel.setVisibility(View.GONE);
        selectPannel.setVisibility(View.VISIBLE);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        double width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth() * 0.72;
        lp.width = (int) width;
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
    }

    public void finishSelect(Bitmap bitmap) {
        showImg.setImageBitmap(bitmap);
        imgShowPannel.setVisibility(View.VISIBLE);
        selectPannel.setVisibility(View.GONE);
        currentBitmap = bitmap;
    }


    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void initDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_image_channel, null);
        dialog.setContentView(view);
        initView();
    }

    private void initView() {
        albumPannel = dialog.findViewById(R.id.album_pannel);
        cameraPannel = dialog.findViewById(R.id.camera_pannel);
        close = dialog.findViewById(R.id.close);
        showImg = dialog.findViewById(R.id.selected_img);
        cancel = dialog.findViewById(R.id.cancel);
        certain = dialog.findViewById(R.id.certain);
        imgShowPannel = dialog.findViewById(R.id.img_show_pannel);
        selectPannel = dialog.findViewById(R.id.select_pannel);
        albumPannel.setOnClickListener(this);
        cameraPannel.setOnClickListener(this);
        close.setOnClickListener(this);
        cancel.setOnClickListener(this);
        certain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.camera_pannel:
                Log.e(TAG, "点击相机");
                clickCamera();
                break;
            case R.id.album_pannel:
                Log.e(TAG, "点击相册");
                clickAlbum();
                break;
            case R.id.close:
                dismissDialog();
                break;
            case R.id.cancel:
                dismissDialog();
                break;
            case R.id.certain:
                Log.e(TAG, "点击确定");
                clickCertain();
                break;
        }
    }

    private void clickCertain() {
        LoadingUtils.getINSTANCE(mContext).showLoadingViewGhost();
        UploadFile uploadFile = new UploadFile();
        //上传歌曲，account传入歌曲id
        uploadFile.setAccount(String.valueOf(song.getSongId()));
        //随便填 我的后台会重新命名， 但后缀要正确,即文件格式一定要正确
        uploadFile.setFileName("song_avatar.jpg");
        uploadFile.setFileStr(FileUtils.bitmapToBase64(currentBitmap));
        mainPresenter.changeSongAvatar(uploadFile);
    }

    private void clickAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity)mContext).startActivityForResult(intent, TAKE_ALBUM_SONG);
    }

    private void clickCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), "/ShareLightTemp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imgUri = FileProvider.getUriForFile(mContext,
                AUTHORITY, file);
        Intent intent = new Intent();
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        ((Activity) mContext).startActivityForResult(intent, TAKE_PHOTO_SONG);
    }

    public Uri getImgUri() {
        return imgUri;
    }
}

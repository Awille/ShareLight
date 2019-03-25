package com.example.will.sharelight.main.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.will.sharelight.BuildConfig;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainPresenterImpl;

import java.io.File;

public class ImageSelectChannelDialogMrg implements View.OnClickListener {
    private static final String TAG = "ImageSelectChannelDialo";
    private Dialog dialog;
    private Context mContext;
    private LinearLayout albumPannel;
    private LinearLayout cameraPannel;
    private ImageView close;

    public static final int TAKE_PHOTO = 1;
    public static final int TAKE_ALBUM = 2;

    public ImageSelectChannelDialogMrg(Context context) {
        this.mContext = context;
    }

    public void showDialog() {
        if (dialog == null) {
            initDialog();
        }
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        double width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth() * 0.72;
        lp.width = (int) width;
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
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
        albumPannel.setOnClickListener(this);
        cameraPannel.setOnClickListener(this);
        close.setOnClickListener(this);
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
                break;
            case R.id.close:
                dismissDialog();
                break;
        }
    }

    private void clickCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), "/externalPath/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = FileProvider.getUriForFile(mContext,
                BuildConfig.APPLICATION_ID + ".fileprovider", file);
        Intent intent = new Intent();
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        ((Activity) mContext).startActivityForResult(intent, TAKE_PHOTO);
    }
}

package com.example.will.utils.loadingutils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.will.utils.R;

public class LoadingUtils {

    private static LoadingUtils INSTANCE;

    private Context context;

    private Dialog loadingDialog;

    private LVNews lvNews;
    private LVGhost lvGhost;

    public static LoadingUtils getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (LoadingUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoadingUtils(context);
                }
            }
        }
        return INSTANCE;
    }


    public void showLoadingViewNews() {
        if (loadingDialog == null) {
            initDialog();
        }
        lvGhost.setVisibility(View.GONE);
        lvNews.setVisibility(View.VISIBLE);
        lvNews.startAnim();
        loadingDialog.show();
    }

    public void showLoadingViewGhost() {
        if (loadingDialog == null) {
            initDialog();
        }
        lvGhost.setVisibility(View.VISIBLE);
        lvNews.setVisibility(View.GONE);
        lvGhost.startAnim();
        loadingDialog.show();
    }

    public void dismisDialog() {
        if (loadingDialog != null) {
            lvGhost.stopAnim();
            lvNews.stopAnim();
            loadingDialog.dismiss();
        }

    }

    private LoadingUtils(Context context) {
        this.context = context;
    }

    private void initDialog() {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        loadingDialog.setContentView(view);
        initView();


//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(loadingDialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        loadingDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loadingDialog.setCancelable(false);

    }

    private void initView() {
        lvGhost = loadingDialog.findViewById(R.id.lv_goast);
        lvNews = loadingDialog.findViewById(R.id.lv_news);
        lvGhost.setViewColor(Color.WHITE);
        lvGhost.setHandColor(Color.BLACK);
        lvNews.setViewColor(Color.WHITE);
    }
}

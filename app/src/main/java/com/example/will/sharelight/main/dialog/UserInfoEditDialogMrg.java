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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.user.User;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainPresenterImpl;
import com.example.will.utils.CircleImageView;
import com.example.will.utils.TextUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.segmentControl.SegmentControl;
import com.example.will.utils.smoothcheckbox.SmoothCheckBox;
import com.example.will.utils.toast.ToastUtils;

public class UserInfoEditDialogMrg implements View.OnClickListener {
    private Dialog dialog;

    private Context mContext;

    private MainPresenterImpl mainPresenter;

    private int mode;

    private CircleImageView userAvatar;
    private EditText userNameEdit;
    private ImageView genderImg;
    private EditText descriptionEdit;
    private Button certain;
    private Button cancel;

    private int genderSelect;

    private SegmentControl segmentControl;


    public UserInfoEditDialogMrg(Context context, MainPresenterImpl mainPresenter) {
        this.mainPresenter = mainPresenter;
        mContext = context;
    }

    private void initDialog(Context context) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_info_dialog, null);
        dialog.setContentView(view);
        initView();
    }

    public static UserInfoEditDialogMrg build(Context context, MainPresenterImpl mainPresenter) {
        return new UserInfoEditDialogMrg(context, mainPresenter);
    }

    public void showUserInfoEditDialog(int mode) {
        this.mode = mode;
        if (dialog == null) {
            initDialog(mContext);
        }
        updateDialog(mode);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        double width = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth() * 0.72;
        lp.width = (int) width;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void dimissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void initView() {
        userAvatar = dialog.findViewById(R.id.user_avatar);
        userNameEdit = dialog.findViewById(R.id.user_name);
        genderImg = dialog.findViewById(R.id.gender_image);
        descriptionEdit = dialog.findViewById(R.id.description);
        cancel = dialog.findViewById(R.id.cancel);
        certain = dialog.findViewById(R.id.certain);
        segmentControl = dialog.findViewById(R.id.gender_select);

        cancel.setOnClickListener(this);
        certain.setOnClickListener(this);
    }

    /**
     * 根据不同的行为更新dialog
     * @param mode  1: 更改名字与性别  2: 更改个性签名
     */
    private void updateDialog(int mode) {
        if (mode == 1) {
            segmentControl.setVisibility(View.VISIBLE);
            genderImg.setVisibility(View.INVISIBLE);
            userNameEdit.setEnabled(true);
            descriptionEdit.setEnabled(false);
        } else {
            genderImg.setVisibility(View.VISIBLE);
            segmentControl.setVisibility(View.INVISIBLE);
            userNameEdit.setEnabled(false);
            descriptionEdit.setEnabled(true);
        }

        User user = MusicDataContext.getINSTANCE().getUser();
        genderSelect = user.getGender();
        userNameEdit.setText(user.getNickName());
        userNameEdit.setSelection(user.getNickName().length());
        if (!TextUtils.isEmpty(user.getAvatarUrl())) {
            ImageLoader.build(mContext).bindBitmap(RetrofitMrg.baseUrl + user.getAvatarUrl(), userAvatar, 200, 200);
        }

        if (!TextUtils.isEmpty(user.getSignature())) {
            descriptionEdit.setText(user.getSignature());
            descriptionEdit.setSelection(user.getSignature().length());
        }
        if (genderSelect == 1) {
            genderImg.setImageResource(R.drawable.male);
            segmentControl.setSelectedIndex(1);
        } else if (genderSelect == 2) {
            genderImg.setImageResource(R.drawable.female);
            segmentControl.setSelectedIndex(2);
        } else {
            genderImg.setImageResource(R.drawable.unknow_sexual);
            segmentControl.setSelectedIndex(0);
        }
        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                genderSelect = index;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.cancel:
                dimissDialog();
                break;
            case R.id.certain:
                clickActionBtn();
                break;
        }
    }

    private void clickActionBtn() {
        if (mode == 1) {
            //只修改姓名 性别
            String nickName = userNameEdit.getText().toString();
            if (TextUtils.isEmpty(nickName)){
                ToastUtils.showWarningToast(mContext, "昵称不能为空", ToastUtils.LENGTH_SHORT);
            } else {
                User updateUser = MusicDataContext.getINSTANCE().getUser();
                updateUser.setNickName(nickName);
                updateUser.setGender(genderSelect);
                mainPresenter.updateUserInfo(updateUser);
                LoadingUtils.getINSTANCE(mContext).showLoadingViewGhost();
            }
        } else {
            String signature = descriptionEdit.getText().toString();
            if (TextUtils.isEmpty(signature)) {
                ToastUtils.showWarningToast(mContext, "签名不能为空", ToastUtils.LENGTH_SHORT);
            } else {
                User updateUser = MusicDataContext.getINSTANCE().getUser();
                updateUser.setSignature(signature);
                mainPresenter.updateUserInfo(updateUser);
                LoadingUtils.getINSTANCE(mContext).showLoadingViewGhost();
            }
        }
    }

}

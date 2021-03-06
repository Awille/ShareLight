package com.example.will.sharelight.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.will.datacontext.MusicDataContext;
import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.user.User;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.utils.TextUtils;
import com.example.will.utils.TimeUtils;
import com.example.will.utils.encrypt.EncryptUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.sharepreferencehelper.SharePreferenceHelper;
import com.example.will.utils.toast.ToastUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.LoginView {
    private TextView loginTitle;
    private EditText accountEdit;
    private LinearLayout nickPannel;
    private EditText nickNameEdit;
    private EditText passwordEdit;
    private Button actionButton;
    private LinearLayout hintSignUpPannel;
    private TextView signUpButton;
    private ImageView closeSignUpBt;

    /**
     * 0 : sign in
     * 1 : sign up
     */
    private static int status = 0;

    private LoginPresenterImpl loginPresenter;

    private static final int REQUEST_PERMISSION =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterImpl(this);
        initView();
        checkPermission();
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA )
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)
                    || !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtils.showWarningToast(this, "请手动开启相关权限", ToastUtils.LENGTH_LONG);
            }
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_PERMISSION);
        } else {
            if (SharePreferenceHelper.getLoginStatus() == true) {
                MusicDataContext.getINSTANCE().setUser(JSON.parseObject(SharePreferenceHelper.getUserInfoSerial(), User.class));
                loginPresenter.pullUserInfo(MusicDataContext.getINSTANCE().getUser().getAccount());
                LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
            }
        }
    }


    void enterMainActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    void initView() {
        loginTitle = findViewById(R.id.login_title);
        accountEdit = findViewById(R.id.account);
        nickPannel = findViewById(R.id.nick_pannel);
        nickNameEdit = findViewById(R.id.nick_name);
        passwordEdit = findViewById(R.id.password);
        actionButton = findViewById(R.id.action_bt);
        actionButton.setOnClickListener(this);
        hintSignUpPannel = findViewById(R.id.hint_sign_up);
        signUpButton = findViewById(R.id.sign_up_bt);
        signUpButton.setOnClickListener(this);
        closeSignUpBt = findViewById(R.id.close_sign_up);
        closeSignUpBt.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.action_bt:
                clickActionButton();
                break;
            case R.id.sign_up_bt:
                changeStatus();
                break;
            case R.id.close_sign_up:
                changeStatus();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void clickActionButton() {
        if (status == 0) {
            String account = accountEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
            accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
                loginPresenter.signIn(account, EncryptUtils.encryptByMd5(password));
            } else {
                ToastUtils.showWarningToast(this, "输入不能为空", ToastUtils.LENGTH_SHORT);
                if (TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                    accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(account)) {
                    passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                } else {
                    passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                    accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                }
            }
        } else {
            String account = accountEdit.getText().toString();
            String nickName = nickNameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
            nickNameEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
            passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(nickName) && !TextUtils.isEmpty(password)) {
                LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
                loginPresenter.signUp(account, nickName, EncryptUtils.encryptByMd5(password));
            } else {
                ToastUtils.showWarningToast(this, "输入不能为空", ToastUtils.LENGTH_SHORT);
                if (TextUtils.isEmpty(account)) {
                    accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                }
                if (TextUtils.isEmpty(nickName)) {
                    nickNameEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_wrong));
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean flag = true;
        if (requestCode == REQUEST_PERMISSION) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    flag = false;
                }
            }
        }
        if (flag) {
            if (SharePreferenceHelper.getLoginStatus() == true) {
                MusicDataContext.getINSTANCE().setUser(JSON.parseObject(SharePreferenceHelper.getUserInfoSerial(), User.class));
                loginPresenter.pullUserInfo(MusicDataContext.getINSTANCE().getUser().getAccount());
                LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
            }
        } else {
            ToastUtils.showWarningToast(this, "权限未获得，程序可能崩溃", ToastUtils.LENGTH_LONG);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeStatus() {
        passwordEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
        accountEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
        nickNameEdit.setBackground(getResources().getDrawable(R.drawable.edit_text_background_normal));
        passwordEdit.setText("");
        accountEdit.setText("");
        nickNameEdit.setText("");
        //current :sign in change to sign up
        if (status == 0) {
            status = 1;
            nickPannel.setVisibility(View.VISIBLE);
            hintSignUpPannel.setVisibility(View.GONE);
            actionButton.setText("Sign up");
            loginTitle.setText("Sign up");
            closeSignUpBt.setVisibility(View.VISIBLE);
        } else if (status == 1){
            //current :sign up change to sign in
            status = 0;
            nickPannel.setVisibility(View.GONE);
            hintSignUpPannel.setVisibility(View.VISIBLE);
            actionButton.setText("Sign in");
            loginTitle.setText("Sign in");
            closeSignUpBt.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onSignUpSuccess(User user) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "注册成功", ToastUtils.LENGTH_LONG);
        changeStatus();
    }

    @Override
    public void onSignUpFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onSignInSuccess(User user) {
        MusicDataContext.getINSTANCE().setUser(user);
        SharePreferenceHelper.setLoginStatus(true);
        SharePreferenceHelper.setUserInfo(user);
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "登录成功", ToastUtils.LENGTH_SHORT);
        enterMainActivity();
    }

    @Override
    public void onSignInFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onPullUserInfoSuccess(User user) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        if (MusicDataContext.getINSTANCE().getUser().getPassword().equals(user.getPassword())) {
            //直接登录
            MusicDataContext.getINSTANCE().setUser(user);
            ToastUtils.showSuccessToast(this, "自动登录", ToastUtils.LENGTH_SHORT);
            SharePreferenceHelper.setUserInfo(user);
            enterMainActivity();
        } else {
            SharePreferenceHelper.setLoginStatus(false);
            ToastUtils.showErrorToast(this, "缓存信息失效，请重新登录", ToastUtils.LENGTH_SHORT);
        }
    }

    @Override
    public void onPullUserInfoFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        SharePreferenceHelper.setLoginStatus(false);
        ToastUtils.showErrorToast(this, "缓存信息失效，请重新登录", ToastUtils.LENGTH_SHORT);
    }
}

package com.example.will.sharelight.login;

import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.example.will.sharelight.TextUtils;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.utils.encrypt.EncryptUtils;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterImpl(this);
        initView();

        if (SharePreferenceHelper.getLoginStatus() == true) {
            MusicDataContext.getINSTANCE().setUser(JSON.parseObject(SharePreferenceHelper.getUserInfoSerial(), User.class));
            enterMainActivity();
        }
    }


    void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
        ToastUtils.showSuccessToast(this, "注册成功", ToastUtils.LENGTH_LONG);
        changeStatus();
    }

    @Override
    public void onSignUpFail(String errCode, String errMsg) {
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onSignInSuccess(User user) {
        MusicDataContext.getINSTANCE().setUser(user);
        SharePreferenceHelper.setLoginStatus(true);
        SharePreferenceHelper.setUserInfo(user);
        ToastUtils.showSuccessToast(this, "登录成功", ToastUtils.LENGTH_SHORT);
        enterMainActivity();
    }

    @Override
    public void onSignInFail(String errCode, String errMsg) {
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }
}

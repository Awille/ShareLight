package com.example.will.sharelight.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.sharelight.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.action_bt:

                break;
            case R.id.sign_up_bt:
                changeStatus();
                break;
            case R.id.close_sign_up:
                changeStatus();
                break;
        }
    }

    private void changeStatus() {
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


}

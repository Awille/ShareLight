package com.example.will.sharelight.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.sharelight.R;
import com.example.will.utils.CircleImageView;
import com.example.will.utils.MyTextView;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CircleImageView userAvatar;
    private ImageView sexual;
    private MyTextView userName;
    private LinearLayout changeAvatarPannel;
    private LinearLayout birthPannel;
    private MyTextView birthText;
    private LinearLayout uploadPannel;
    private LinearLayout signaturePannel;
    private TextView description;

    private SideBar leftSideBar;

    private Context context;

    private MyFragmentPagerAdapter fragmentPagerAdapter;

    private static HomeFragment homeFragment;
    private static SquareFragment squareFragment;

    private TabLayout.Tab home;
    private TabLayout.Tab square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        toolBarInit();
        setLeftSideBarListener();
        context = this;
    }

    @SuppressLint("NewApi")
    void initView() {
        //id绑定
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        userAvatar = (CircleImageView) findViewById(R.id.user_avatar);
        sexual = (ImageView) findViewById(R.id.sexual);
        userName = (MyTextView) findViewById(R.id.user_name);
        changeAvatarPannel = (LinearLayout) findViewById(R.id.change_avatar_pannel);
        birthPannel = (LinearLayout) findViewById(R.id.birth_pannel);
        birthText = (MyTextView) findViewById(R.id.birth_text);
        uploadPannel = (LinearLayout) findViewById(R.id.upload_pannel);
        signaturePannel = (LinearLayout) findViewById(R.id.signature_pannel);
        description = (TextView) findViewById(R.id.description);
        leftSideBar = (SideBar) findViewById(R.id.leftSideBar);

        //viewpager加载
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        //tab绑定
        home = mTabLayout.getTabAt(0);
        square = mTabLayout.getTabAt(1);
        home.setIcon(R.drawable.home);
        square.setIcon(R.drawable.music);

    }

    private void toolBarInit() {
        setSupportActionBar(mToolBar);
        final DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(getResources().getColor(R.color.FFFFFF));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Log.e(TAG, "actionbar 设定");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(drawerArrowDrawable);
        }

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (((ViewGroup)drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    drawerArrowDrawable.setProgress(slideOffset);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.homeAsUp:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    public static HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    public static SquareFragment getSquareFragment() {
        if (squareFragment == null) {
            squareFragment = new SquareFragment();
        }
        return squareFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    private void setLeftSideBarListener() {
        leftSideBar.setFantasyListener(new FantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                return false;
            }

            @Override
            public boolean onSelect(View view, int index) {
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.userInfo:
                        Log.e(TAG, "名字、性别修改");
                        LoadingUtils.getINSTANCE(context).showLoadingViewNews();
                        break;
                    case R.id.change_avatar_pannel:
                        Log.e(TAG, "更改头像");
                        LoadingUtils.getINSTANCE(context).showLoadingViewGhost();
                        break;
                    case R.id.birth_pannel:
                        Log.e(TAG, "生日");
                        break;
                    case R.id.upload_pannel:
                        Log.e(TAG, "上传");
                        break;
                    case R.id.signature_pannel:
                        Log.e(TAG, "签名");
                        break;
                }
                return false;
            }

            @Override
            public void onCancel() {

            }
        });
    }

}

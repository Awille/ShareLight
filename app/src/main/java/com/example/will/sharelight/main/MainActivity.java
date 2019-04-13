package com.example.will.sharelight.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.will.datacontext.MusicDataContext;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.imageloader.ImageResizer;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.user.User;
import com.example.will.sharelight.R;
import com.example.will.sharelight.login.LoginActivity;
import com.example.will.sharelight.main.dialog.AddSongDialogMrg;
import com.example.will.sharelight.main.dialog.ImageSelectChannelDialogMrg;
import com.example.will.sharelight.main.dialog.SongAvatarSelectChannelDialog;
import com.example.will.sharelight.main.dialog.SongListAvatarSelectDialog;
import com.example.will.sharelight.main.dialog.SongListSettingDialog;
import com.example.will.sharelight.main.dialog.SongSettingDialog;
import com.example.will.sharelight.main.dialog.UploadSongResourceDialog;
import com.example.will.sharelight.main.dialog.UserInfoEditDialogMrg;
import com.example.will.sharelight.main.homefragment.HomeFragment;
import com.example.will.sharelight.main.square.SquareFragment;
import com.example.will.utils.CircleImageView;
import com.example.will.utils.FileUtils;
import com.example.will.utils.MyTextView;
import com.example.will.utils.TextUtils;
import com.example.will.utils.TimeUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.sharepreferencehelper.SharePreferenceHelper;
import com.example.will.utils.toast.ToastUtils;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, MainContract.MainView {

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

    private UserInfoEditDialogMrg userInfoEditDialogMrg;
    private ImageSelectChannelDialogMrg imageSelectChannelDialogMrg;
    private AddSongDialogMrg addSongDialogMrg;
    private UploadSongResourceDialog uploadSongResourceDialog;
    private SongAvatarSelectChannelDialog songAvatarSelectChannelDialog;
    private SongListAvatarSelectDialog songListAvatarSelectDialog;
    private SongListSettingDialog songListSettingDialog;
    private SongSettingDialog songSettingDialog;

    private MyFragmentPagerAdapter fragmentPagerAdapter;

    private static HomeFragment homeFragment;
    private static SquareFragment squareFragment;

    private TabLayout.Tab home;
    private TabLayout.Tab square;

    private MainPresenterImpl mainPresenter;

    private RecyclerView songListRecyclerView;


    public interface HomeFragmentListener {
        void onUserInfoChange();
        void onUploadSongChange();
        void onChangeSongList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this);
        userInfoEditDialogMrg = UserInfoEditDialogMrg.build(this, mainPresenter);
        imageSelectChannelDialogMrg = new ImageSelectChannelDialogMrg(this, mainPresenter);
        addSongDialogMrg = new AddSongDialogMrg(this, mainPresenter);
        songSettingDialog = new SongSettingDialog(this);
        uploadSongResourceDialog = new UploadSongResourceDialog(this, mainPresenter);
        songListSettingDialog = new SongListSettingDialog(this, mainPresenter);
        songListAvatarSelectDialog = new SongListAvatarSelectDialog(this, mainPresenter);
        //记住使用前一定要setsong
        songAvatarSelectChannelDialog = new SongAvatarSelectChannelDialog(this, mainPresenter);
        initView();
        toolBarInit();
        setLeftSideBarListener();
    }

    public SongListSettingDialog getSongListSettingDialog() {
        return songListSettingDialog;
    }

    public SongSettingDialog getSongSettingDialog() {
        return songSettingDialog;
    }

    public SongAvatarSelectChannelDialog getSongAvatarSelectChannelDialog() {
        return songAvatarSelectChannelDialog;
    }

    public SongListAvatarSelectDialog getSongListAvatarSelectDialog() {
        return songListAvatarSelectDialog;
    }



    @SuppressLint("NewApi")
    void initView() {
        //id绑定
        drawerLayout = findViewById(R.id.drawer_layout);
        mToolBar = findViewById(R.id.tool_bar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        userAvatar = findViewById(R.id.user_avatar);
        sexual = findViewById(R.id.sexual);
        userName = findViewById(R.id.user_name);
        changeAvatarPannel = findViewById(R.id.change_avatar_pannel);
        birthPannel = findViewById(R.id.birth_pannel);
        birthText = findViewById(R.id.birth_text);
        uploadPannel = findViewById(R.id.upload_pannel);
        signaturePannel = findViewById(R.id.signature_pannel);
        description = findViewById(R.id.description);
        leftSideBar = findViewById(R.id.leftSideBar);
        songListRecyclerView = findViewById(R.id.song_list_recycler_view);

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

    private void updateSideBar() {
        birthText.setText(MusicDataContext.getINSTANCE().getUser().getBirth());
        description.setText(MusicDataContext.getINSTANCE().getUser().getSignature());
        userName.setText(MusicDataContext.getINSTANCE().getUser().getNickName());
        int userGender = MusicDataContext.getINSTANCE().getUser().getGender();
        if (userGender == 1) {
            sexual.setImageResource(R.drawable.male);
        } else if (userGender == 2) {
            sexual.setImageResource(R.drawable.female);
        } else {
            sexual.setImageResource(R.drawable.unknow_sexual);
        }
        String avatarUrl = MusicDataContext.getINSTANCE().getUser().getAvatarUrl();
        if (!TextUtils.isEmpty(avatarUrl)) {
            ImageLoader.build(this).bindBitmap(RetrofitMrg.baseUrl + avatarUrl, userAvatar, 200, 200);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            updateSideBar();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        } else if (item.getItemId() == R.id.menu_add_song_list) {
            Log.e(TAG, "添加歌单");
        } else if (item.getItemId() == R.id.menu_upload) {
            Log.e(TAG, "点击创作中心");
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
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
                        editUserInfo(1);
                        break;
                    case R.id.change_avatar_pannel:
                        imageSelectChannelDialogMrg.showDialog();
                        break;
                    case R.id.birth_pannel:
                        showDatePickerDialog();
                        break;
                    case R.id.upload_pannel:
                        Log.e(TAG, "上传");
                        addSongDialogMrg.showDialog();
                        break;
                    case R.id.signature_pannel:
                        editUserInfo(2);
                        break;
                    case R.id.exit_pannel: {
                        exitUser();
                        break;
                    }
                }
                return false;
            }

            @Override
            public void onCancel() {

            }
        });
    }
    //注销
    private void exitUser() {
        SharePreferenceHelper.setLoginStatus(false);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void editUserInfo(int mode) {
        userInfoEditDialogMrg.showUserInfoEditDialog(mode);
    }

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        String birth = MusicDataContext.getINSTANCE().getUser().getBirth();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                Integer.parseInt(birth.substring(0,4)),
                Integer.parseInt(birth.substring(5,7)) - 1,
                Integer.parseInt(birth.substring(8,10)));
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
    }







    @SuppressLint("NewApi")
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        User temp = MusicDataContext.getINSTANCE().getUser();
        temp.setBirth(TimeUtils.int2StringFormat1(year, monthOfYear, dayOfMonth));
        mainPresenter.updateUserInfo(temp);
        LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
    }

    @Override
    public void onUpdateUserInfoSuccess(User user) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "更新成功", ToastUtils.LENGTH_SHORT);
        //更新上下文
        MusicDataContext.getINSTANCE().setUser(user);
        userInfoEditDialogMrg.dimissDialog();
        //fragment信息更新
        homeFragment.onUserInfoChange();
    }

    @Override
    public void onUpdateUserInfoFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onChangeUserAvatarSuccess() {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        imageSelectChannelDialogMrg.dismissDialog();
        ToastUtils.showSuccessToast(this, "上传成功", ToastUtils.LENGTH_LONG);
        //fragment信息更新
        homeFragment.onUserInfoChange();
    }

    @Override
    public void onChangeUserAvatarFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        imageSelectChannelDialogMrg.dismissDialog();
        ToastUtils.showErrorToast(this, "上传失败", ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onAddSongSuccess(Song song) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        addSongDialogMrg.dismissDialog();
        ToastUtils.showSuccessToast(this, "添加成功，请于上传列表对资源进行更新", ToastUtils.LENGTH_LONG);
        homeFragment.onUploadSongChange();
    }

    @Override
    public void onAddSongFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        addSongDialogMrg.dismissDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_SHORT);
    }

    @Override
    public void onChangeSongAvatarSuccess() {
        ToastUtils.showSuccessToast(this, "上传成功", ToastUtils.LENGTH_SHORT);
        LoadingUtils.getINSTANCE(this).dismisDialog();
        songAvatarSelectChannelDialog.dismissDialog();
        homeFragment.onUploadSongChange();
    }

    @Override
    public void onChangeSongAvatarFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        songAvatarSelectChannelDialog.dismissDialog();
        ToastUtils.showErrorToast(this,"上传失败", ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onChangeSongResourceSuccess() {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "上传成功", ToastUtils.LENGTH_SHORT);
        uploadSongResourceDialog.dismissDialog();
        //主界面更新
        homeFragment.onUploadSongChange();
    }

    @Override
    public void onChangeSongResourceFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, "上传失败", ToastUtils.LENGTH_LONG);
        uploadSongResourceDialog.dismissDialog();
    }

    @Override
    public void onChangeSongListAvatarSuccess() {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        songListAvatarSelectDialog.dismissDialog();
        ToastUtils.showSuccessToast(this, "更新成功", ToastUtils.LENGTH_SHORT);
        homeFragment.onChangeSongList();
    }

    @Override
    public void onChangeSongListAvatarFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, "上传失败", ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onDeleteSongListSuccess() {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "删除成功", ToastUtils.LENGTH_SHORT);
        homeFragment.onChangeSongList();
    }

    @Override
    public void onDeleteSongListFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, "删除失败", ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onChangeSongListNameSuccess() {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showSuccessToast(this, "修改成功", ToastUtils.LENGTH_SHORT);
        homeFragment.onChangeSongList();
    }

    @Override
    public void onChangeSongListNameFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, "修改失败", ToastUtils.LENGTH_LONG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageSelectChannelDialogMrg.TAKE_PHOTO_USER: {
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(
                                imageSelectChannelDialogMrg.getImgUri()), 200, 200);
                        if (bitmap == null) {
                            ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                            imageSelectChannelDialogMrg.dismissDialog();
                        } else {
                            imageSelectChannelDialogMrg.finishSelect(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_SHORT);
                        imageSelectChannelDialogMrg.dismissDialog();
                    }
                }
                break;
            }
            case ImageSelectChannelDialogMrg.TAKE_ALBUM_USER: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imgUri = data.getData();
                        if (imgUri != null) {
                            Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(imgUri),
                                    200, 200);
                            if (bitmap == null) {
                                ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                                imageSelectChannelDialogMrg.dismissDialog();
                            } else {
                                imageSelectChannelDialogMrg.finishSelect(bitmap);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相册错误", ToastUtils.LENGTH_SHORT);
                        imageSelectChannelDialogMrg.dismissDialog();
                    }
                }
                break;
            }
            case SongAvatarSelectChannelDialog.TAKE_PHOTO_SONG: {
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(
                                songAvatarSelectChannelDialog.getImgUri()), 200, 200);
                        if (bitmap == null) {
                            ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                            songAvatarSelectChannelDialog.dismissDialog();
                        } else {
                            songAvatarSelectChannelDialog.finishSelect(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_SHORT);
                        songAvatarSelectChannelDialog.dismissDialog();
                    }
                }
                break;
            }
            case SongAvatarSelectChannelDialog.TAKE_ALBUM_SONG: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imgUri = data.getData();
                        if (imgUri != null) {
                            Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(imgUri),
                                    200, 200);
                            if (bitmap == null) {
                                ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                                songAvatarSelectChannelDialog.dismissDialog();
                            } else {
                                songAvatarSelectChannelDialog.finishSelect(bitmap);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相册错误", ToastUtils.LENGTH_SHORT);
                        songAvatarSelectChannelDialog.dismissDialog();
                    }
                }
                break;
            }
            case SongListAvatarSelectDialog.TAKE_ALBUM_SONG_LIST: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imgUri = data.getData();
                        if (imgUri != null) {
                            Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(imgUri),
                                    200, 200);
                            if (bitmap == null) {
                                ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                                songListAvatarSelectDialog.dismissDialog();
                            } else {
                                songListAvatarSelectDialog.finishSelect(bitmap);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相册错误", ToastUtils.LENGTH_SHORT);
                        songListAvatarSelectDialog.dismissDialog();
                    }
                }
                break;
            }
            case SongListAvatarSelectDialog.TAKE_PHOTO_SONG_LIST: {
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = ImageResizer.decodeSampleBitmapFromStream(getContentResolver().openInputStream(
                                songListAvatarSelectDialog.getImgUri()), 200, 200);
                        if (bitmap == null) {
                            ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_LONG);
                            songListAvatarSelectDialog.dismissDialog();
                        } else {
                            songListAvatarSelectDialog.finishSelect(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtils.showErrorToast(this, "相机错误", ToastUtils.LENGTH_SHORT);
                        songListAvatarSelectDialog.dismissDialog();
                    }
                }
                break;
            }
            case SongSettingDialog.SELECT_SONG_RESOURCE: {
                Uri uri = data.getData();
                try {
                    String path = FileUtils.getPath(this, uri);
                    songSettingDialog.dismissDialog();
                    uploadSongResourceDialog.showDialog(path, songSettingDialog.getSong());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

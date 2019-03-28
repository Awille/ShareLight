package com.example.will.sharelight.main.homefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.will.datacontext.MusicDataContext;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.songlist.SongList;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.sharelight.main.adapter.SongAdapter;
import com.example.will.sharelight.main.adapter.SongListAdapter;
import com.example.will.utils.CircleImageView;
import com.example.will.utils.MyTextView;
import com.example.will.utils.TextUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, HomeContract.HomeView, MainActivity.HomeFragmentListener {

    private static final String TAG = "HomeFragment";

    private static final int LIST_OPEN = 1;
    private static final int LIST_CLOSE = 2;

    private CircleImageView userAvatar;
    private MyTextView userName;
    private ImageView genderImg;
    private ImageView songListSetting;
    private LinearLayout hideListPannel;
    private ImageView  hideListImg;
    private RecyclerView songListRecyclerView;
    private LinearLayout hideUploadSongPannel;
    private ImageView hideUploadImg;
    private RecyclerView uploadSongRecylerView;

    private int songListStatus = LIST_OPEN;
    private int uploadListStatus = LIST_OPEN;

    private HomePrensterImpl homePrenster;

    private List<SongList> songLists = new ArrayList<>();
    private SongListAdapter songListAdapter;
    private List<Song> uploadSongs = new ArrayList<>();
    private SongAdapter songAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView(view);
        homePrenster = new HomePrensterImpl(this);
        homePrenster.querySongLists(MusicDataContext.getINSTANCE().getUser().getUserId());
        homePrenster.queryUploadSongs(MusicDataContext.getINSTANCE().getUser().getUserId());

        return view;
    }

    private void updateUserInfo() {
        if (!TextUtils.isEmpty(MusicDataContext.getINSTANCE().getUser().getAvatarUrl())) {
            ImageLoader.build(getActivity()).bindBitmap(RetrofitMrg.baseUrl + MusicDataContext.getINSTANCE().getUser().getAvatarUrl(),
                    userAvatar,
                    100, 100);
        } else {
            userAvatar.setImageResource(R.drawable.image_loading);
        }
        userName.setText(MusicDataContext.getINSTANCE().getUser().getNickName());
        if (MusicDataContext.getINSTANCE().getUser().getGender() == 0) {
            genderImg.setImageResource(R.drawable.unknow_sexual);
        } else if (MusicDataContext.getINSTANCE().getUser().getGender() == 1) {
            genderImg.setImageResource(R.drawable.male);
        } else {
            genderImg.setImageResource(R.drawable.female);
        }
    }

    private void initView(View view) {
        userAvatar = view.findViewById(R.id.user_avatar);
        userName = view.findViewById(R.id.user_name);
        genderImg = view.findViewById(R.id.gender_image);
        songListSetting = view.findViewById(R.id.song_list_setting);
        hideListPannel = view.findViewById(R.id.hide_list_pannel);
        hideListImg = view.findViewById(R.id.hide_list_img);
        songListRecyclerView = view.findViewById(R.id.song_list_recycler_view);
        hideUploadSongPannel = view.findViewById(R.id.hide_upload_pannel);
        hideUploadImg = view.findViewById(R.id.hide_upload_img);
        uploadSongRecylerView = view.findViewById(R.id.upload_song_recycler_view);

        LinearLayoutManager linearLayoutManagerForSongList = new LinearLayoutManager(getActivity());
        songListRecyclerView.setLayoutManager(linearLayoutManagerForSongList);
        songListAdapter = new SongListAdapter(songLists, getActivity());
        songListRecyclerView.setAdapter(songListAdapter);

        LinearLayoutManager linearLayoutManagerForUpload = new LinearLayoutManager(getActivity());
        uploadSongRecylerView.setLayoutManager(linearLayoutManagerForUpload);
        songAdapter = new SongAdapter(uploadSongs, getActivity());
        uploadSongRecylerView.setAdapter(songAdapter);

        updateUserInfo();




        songListSetting.setOnClickListener(this);
        hideListPannel.setOnClickListener(this);
        hideUploadSongPannel.setOnClickListener(this);
    }

    private void changeSongListStatus() {
        if (songListStatus == LIST_OPEN) {
            songListStatus = LIST_CLOSE;
            hideListImg.setImageResource(R.drawable.right);
            songListRecyclerView.setVisibility(View.GONE);
        } else {
            songListStatus = LIST_OPEN;
            hideListImg.setImageResource(R.drawable.down);
            songListRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void changeUploadListStatus() {
        if (uploadListStatus == LIST_OPEN) {
            uploadListStatus = LIST_CLOSE;
            hideUploadImg.setImageResource(R.drawable.right);
            uploadSongRecylerView.setVisibility(View.GONE);
        } else {
            uploadListStatus = LIST_OPEN;
            hideUploadImg.setImageResource(R.drawable.down);
            uploadSongRecylerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.song_list_setting: {
                Log.e(TAG, "点击了设置按钮");
                break;
            }
            case R.id.hide_list_pannel: {
                changeSongListStatus();
                break;
            }
            case R.id.hide_upload_pannel: {
                changeUploadListStatus();
                break;
            }
        }
    }

    @Override
    public void onQuerySongListsSuccess(List<SongList> songLists) {
        Log.e(TAG, "歌单查询成功 " + JSON.toJSONString(songLists));

        ToastUtils.showSuccessToast(getActivity(), "拉取歌单成功", ToastUtils.LENGTH_SHORT);
        this.songLists = songLists;
        songListAdapter.setSongLists(songLists);
        songListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuerySongListsFail(String errCode, String errMsg) {
        ToastUtils.showErrorToast(getActivity(), errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onQueryUploadSongsSuccess(List<Song> songs) {
        Log.e(TAG, "上传作品查询成功 " + JSON.toJSONString(songs));
        uploadSongs = songs;
        songAdapter.setSongs(songs);
        songAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQueryUploadSongFail(String errCode, String errMsg) {
        ToastUtils.showSuccessToast(getActivity(), errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onUserInfoChange() {
        updateUserInfo();
    }
}
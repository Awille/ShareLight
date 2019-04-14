package com.example.will.sharelight.palyer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.palyer.broadcast.PlayerBroadcast;
import com.example.will.utils.TextUtils;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, PlayerBroadcast.onBroadcastRecieveListener {

    private static final String TAG = "PlayerActivity";

    private HorizontalInfiniteCycleViewPager songViewPager;

    private PlayFragmentAdapter playFragmentAdapter;
    private List<Song> songs = new ArrayList<>();
    private int currentSongIndex;

    private Context context = this;

    private Handler mainHandler = new Handler();

    public PlayerBroadcast getPlayerBroadcast() {
        return playerBroadcast;
    }

    public PlayFragmentAdapter getPlayFragmentAdapter() {
        return playFragmentAdapter;
    }

    private PlayerBroadcast playerBroadcast;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = service;
            //这里线程开启
            GetProgressThread.getINSTANCE(context, mBinder, mainHandler).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceConnection = null;
        }
    };;

    private IBinder mBinder;

    public IBinder getmBinder() {
        return mBinder;
    }


    public HorizontalInfiniteCycleViewPager getSongViewPager() {
        return songViewPager;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play_layout);
        initView();
        initData(getIntent());
        playerBroadcast = new PlayerBroadcast();
        playerBroadcast.setListener(this);
        registerReceiver(playerBroadcast, new IntentFilter(
                CommonConstant.BroadcastName.MUSIC_PREPARED));
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.e(TAG, "调用了onNewIntent");
        initData(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG, "onresume 完成");
    }



    private void initView() {
        songViewPager = findViewById(R.id.song_view_pager);
    }

    private void initData(Intent intent) {
        Bundle bundle = intent.getExtras();
        songs = JSON.parseArray(bundle.getString("SONG_LIST"), Song.class);
        currentSongIndex = bundle.getInt("CURRENT_INDEX");
        //先初始化必要数据
        PlayFragmentAdapter.initPlayFragmentAdapter(true, songs, currentSongIndex);
        playFragmentAdapter = new PlayFragmentAdapter(getSupportFragmentManager());
        songViewPager.setAdapter(playFragmentAdapter);
        songViewPager.setCurrentItem(currentSongIndex);
        songViewPager.addOnPageChangeListener(this);
        Intent serviceIntent = new Intent();
        ComponentName componentName = new ComponentName("com.example.will.sharelight",
                "com.example.will.sharelight.palyer.MusicPlayService");
        serviceIntent.setComponent(componentName);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putString("SONG_LIST", JSON.toJSONString(songs));
        serviceBundle.putInt("CURRENT_INDEX", currentSongIndex);
        serviceIntent.putExtras(serviceBundle);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(TAG, "执行了 onDestry");
        unregisterReceiver(playerBroadcast);
        GetProgressThread.getINSTANCE(this, mBinder, mainHandler).interrupt();
        unbindService(serviceConnection);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        PlayFragmentAdapter.initPlayFragmentAdapter(true, songs, i);
        Log.w(TAG, "onPageSelected " + i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    public boolean getIsPrepared() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int isPrepared = 0;
        try {
            data.writeInt(100);
            mBinder.transact(CommonConstant.MusicPlayAction.MUSIC_PREPARED, data, reply, 0);
            isPrepared = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        if (isPrepared == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean getPlayStatus() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int isPlay = 0;
        try {
            data.setDataPosition(0);
            data.writeInt(100);
            mBinder.transact(CommonConstant.MusicPlayAction.PLAY_STATUS, data, reply, 0);
            reply.readException();
            isPlay = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        if (isPlay == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onSongPrepared(int duration) {
        if (playFragmentAdapter != null) {
            playFragmentAdapter.getmCurrentFragment().onSongPrepared(duration);
        }
    }
}

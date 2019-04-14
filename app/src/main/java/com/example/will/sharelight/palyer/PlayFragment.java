package com.example.will.sharelight.palyer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.palyer.broadcast.PlayerBroadcast;
import com.example.will.utils.TimeUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.List;

public class PlayFragment extends Fragment {

    private static final String TAG = "PlayFragment";
    private CircularFillableLoaders circularFillableLoaders;
    private ImageView action;
    private SeekBar seekBar;

    private LinearLayout progressPannel;
    private TextView progressTxt;
    private TextView durationTxt;


    public boolean isPlay = true;

    private Song currentSong;

    public PlayFragment() {
        super();
    }

    public void setSongProgress(int progress) {
        if (seekBar != null) {
            seekBar.setProgress(progress);
        }
        if (progressTxt != null) {
            progressTxt.setText(TimeUtils.fromS2MS(progress));
        }
        Log.e(TAG, "进度控制 " + progress);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_single_play, container, false);
        initView(view);
        if (getArguments() != null) {
            currentSong = JSON.parseObject(getArguments().getString("CURRENT_SONG"), Song.class);
        }
        return view;
    }


    void initView(View view) {
        circularFillableLoaders = view.findViewById(R.id.circularFillableLoaders);
        action = view.findViewById(R.id.action);
        seekBar = view.findViewById(R.id.seekBarProgress);
        progressTxt = view.findViewById(R.id.song_progress);
        durationTxt = view.findViewById(R.id.song_duration);
        progressPannel = view.findViewById(R.id.progress_pannel);
        progressPannel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setActionStatus(action);
        ImageLoader.build(getActivity()).bindBitmap(RetrofitMrg.baseUrl + currentSong.getAvatarUrl(),
                circularFillableLoaders, 200, 200);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circularFillableLoaders.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayStatus(action);
            }
        });
        LoadingUtils.getINSTANCE(getActivity()).showLoadingViewGhost();
        Log.e(TAG, "onViewCreated");
    }

    public void setActionStatus(ImageView action) {
        if (isPlay) {
            action.setImageResource(R.drawable.pause);
        } else {
            action.setImageResource(R.drawable.start);
        }
    }

    private void changePlayStatus(ImageView action) {
        if (!((PlayerActivity)getActivity()).getIsPrepared()) {
            Log.e(TAG, "未加载完成");
            ToastUtils.showWarningToast(getActivity(), "正在加载，请稍后", ToastUtils.LENGTH_SHORT);
            return;
        }

        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        if (isPlay) {
            try {
                ((PlayerActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.PAUSE, data, reply, 0);
                action.setImageResource(R.drawable.start);
                isPlay = false;
            } catch (RemoteException e) {
                e.printStackTrace();
                ToastUtils.showErrorToast(getActivity(), "Error", ToastUtils.LENGTH_SHORT);
            } finally {
                data.recycle();
                reply.recycle();
            }
        } else {
            try {
                ((PlayerActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.PLAY, data, reply, 0);
                action.setImageResource(R.drawable.pause);
                isPlay = true;
            } catch (RemoteException e) {
                e.printStackTrace();
                ToastUtils.showErrorToast(getActivity(), "Error", ToastUtils.LENGTH_SHORT);
            } finally {
                data.recycle();
                reply.recycle();
            }
        }
    }

    public void onSongPrepared(int duration) {
        progressTxt.setText("0:00");
        durationTxt.setText(TimeUtils.fromS2MS(duration));
        seekBar.setMax(duration);
        progressPannel.setVisibility(View.VISIBLE);
        LoadingUtils.getINSTANCE(getActivity()).dismisDialog();
    }
}

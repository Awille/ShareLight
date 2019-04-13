package com.example.will.sharelight.palyer;

import android.os.Bundle;
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
import android.widget.Toast;

import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.palyer.broadcast.PlayerBroadcast;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.List;

public class PlayFragment extends Fragment implements PlayerBroadcast.onBroadcastRecieveListener {

    private static final String TAG = "PlayFragment";
    private CircularFillableLoaders circularFillableLoaders;
    private ImageView action;
    private LobsterShadeSlider shadeSlider;

    public static boolean isPlay = true;
    public static List<Song> songList;
    public static int currentIndex = 0;

    public static void initPlayFragment(boolean isPlay, List<Song> songList,int currentIndex) {
        setIsPlay(isPlay);
        setSongList(songList);
        setCurrentIndex(currentIndex);
    }

    public static void setIsPlay(boolean isPlay) {
        PlayFragment.isPlay = isPlay;
    }

    public static void setSongList(List<Song> songList) {
        PlayFragment.songList = songList;
    }

    public static void setCurrentIndex(int currentIndex) {
        PlayFragment.currentIndex = currentIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_single_play, container, false);
        initView(view);
        return view;
    }


    void initView(View view) {
        circularFillableLoaders = view.findViewById(R.id.circularFillableLoaders);
        action = view.findViewById(R.id.action);
        shadeSlider = view.findViewById(R.id.shadeslider);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setActionStatus(action);
        ImageLoader.build(getActivity()).bindBitmap(RetrofitMrg.baseUrl + songList.get(currentIndex).getAvatarUrl(),
                circularFillableLoaders, 200, 200);
        shadeSlider.addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(int color) {
                circularFillableLoaders.setColor(color);
            }

            @Override
            public void onColorSelected(int color) {
            }
        });
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayStatus(action);
            }
        });
        ((PlayerActivity)getActivity()).getPlayerBroadcast().setListener(this);
        LoadingUtils.getINSTANCE(getActivity()).showLoadingViewGhost();
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

    @Override
    public void onSongPrepared() {
        LoadingUtils.getINSTANCE(getActivity()).dismisDialog();
    }
}

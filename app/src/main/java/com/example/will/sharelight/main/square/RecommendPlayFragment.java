package com.example.will.sharelight.main.square;

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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.sharelight.main.MainActivity;
import com.example.will.sharelight.palyer.PlayerActivity;
import com.example.will.utils.TimeUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

public class RecommendPlayFragment extends Fragment implements RecommendContract.RecommendView {

    private static final String TAG = "RecommendPlayFragment";
    private CircularFillableLoaders circularFillableLoaders;
    private ImageView action;
    private SeekBar seekBar;

    private RecommendContract.RecommendPresenter recommendPresenter;

    private LinearLayout progressPannel;
    private TextView progressTxt;
    private TextView durationTxt;


    private int colorChange = 0;


    public boolean isPlay = true;

    private Song currentSong;

    public RecommendPlayFragment() {
        super();
    }

    public void setSongProgress(int progress) {
        if (seekBar != null) {
            seekBar.setProgress(progress);
        }
        if (progressTxt != null) {
            progressTxt.setText(TimeUtils.fromS2MS(progress));
        }
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
        seekBar = view.findViewById(R.id.seekBarProgress);
        progressTxt = view.findViewById(R.id.song_progress);
        durationTxt = view.findViewById(R.id.song_duration);
        progressPannel = view.findViewById(R.id.progress_pannel);
        progressPannel.setVisibility(View.INVISIBLE);

        recommendPresenter = new RecommendPresenterImpl(this);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getMax() != 0) {
                    double ratio = 1- (progress * 0.1) / (seekBar.getMax()*0.1);
                    circularFillableLoaders.setProgress((int)(100 * ratio));
                }
                if (fromUser) {
                    seekTo(progress);
                }
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
    }

    public void setActionStatus(ImageView action) {
        if (isPlay) {
            action.setImageResource(R.drawable.pause);
        } else {
            action.setImageResource(R.drawable.start);
        }
    }

    private void changePlayStatus(ImageView action) {
        if (!((MainActivity)getActivity()).getFragmentPagerAdapter().getmSquareFragment().getIsPrepared()) {
            Log.e(TAG, "未加载完成");
            ToastUtils.showWarningToast(getActivity(), "正在加载，请稍后", ToastUtils.LENGTH_SHORT);
            return;
        }

        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        if (isPlay) {
            try {
                ((MainActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.PAUSE, data, reply, 0);
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
                ((MainActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.PLAY, data, reply, 0);
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

    public void onSongPrepared(boolean isSame, int duration) {
        if (!isSame) {
            progressTxt.setText("0:00");
        } else {
            ToastUtils.showWarningToast(getActivity(), "歌曲与上一首相同", ToastUtils.LENGTH_LONG);
        }
        durationTxt.setText(TimeUtils.fromS2MS(duration));
        seekBar.setMax(duration);
        progressPannel.setVisibility(View.VISIBLE);
        LoadingUtils.getINSTANCE(getActivity()).dismisDialog();
    }

    private void seekTo(int i) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(i * 1000);
            ((MainActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.SET_POSITION, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }


    public void playRecommendSong() {
        Log.e(TAG, "准备加载歌曲");
        LoadingUtils.getINSTANCE(getActivity()).showLoadingViewGhost();
        recommendPresenter.getRecommendSong("random");
    }

    public void askServicePlaySong() {
        if (currentSong == null) {
            return;
        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeString(JSON.toJSONString(currentSong));
            ((MainActivity)getActivity()).getmBinder().transact(CommonConstant.MusicPlayAction.PLAY_RECOMMEND_SONG,
                    data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        LoadingUtils.getINSTANCE(getActivity()).showLoadingViewGhost();
    }
    @Override
    public void onGetRecommendSongSuccess(Song song) {
        currentSong = song;
        setActionStatus(action);
        ImageLoader.build(getActivity()).bindBitmap(RetrofitMrg.baseUrl + currentSong.getAvatarUrl(),
                circularFillableLoaders, 200, 200);
        askServicePlaySong();
        Log.e(TAG, "服务加载歌曲");
    }

    @Override
    public void onGetRecommendSongFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(getActivity()).dismisDialog();
        ToastUtils.showErrorToast(getActivity(), errMsg, ToastUtils.LENGTH_LONG);
    }



}

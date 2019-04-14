package com.example.will.sharelight.palyer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.song.Song;
import com.example.will.protocol.songlist.SongList;

import java.io.IOException;
import java.util.List;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.GET_POSITION;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.MUSIC_PREPARED;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PAUSE;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_EXACT_SONG;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_LAST;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_NEXT;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_STATUS;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.SET_POSITION;


//跨进程启动
public class MusicPlayService extends Service {

    private static final String TAG = "MusicPlayService";

    public MediaPlayer mediaPlayer = new MediaPlayer();
    private IBinder mBinder = new MyBinder();

    public List<Song> songList;
    public int currentSongIndex;

    public static boolean isPrepared = false;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind开始");
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Bundle bundle = intent.getExtras();
        songList = JSON.parseArray(bundle.getString("SONG_LIST"), Song.class);
        currentSongIndex = bundle.getInt("CURRENT_INDEX");
        try {
            mediaPlayer.setDataSource(RetrofitMrg.baseUrl + songList.get(currentSongIndex).getResourceUrl());
            mediaPlayer.prepareAsync();
            isPrepared = false;
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                    Bundle broadcastBundle = new Bundle();
                    broadcastBundle.putInt("SONG_DURATION", mediaPlayer.getDuration());
                    broadcastIntent.putExtras(broadcastBundle);
                    sendBroadcast(broadcastIntent);
                    Log.e(TAG, "音乐加载完成");
                    isPrepared = true;
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.e(TAG, "播放完成");
                    playNext();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "onBind结束");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "服务onCreate");
    }

    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && isPrepared) {
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            if (isPrepared && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    private void setPosition(int i) {
        if (mediaPlayer != null && isPrepared) {
            mediaPlayer.seekTo(i);
        }
    }

    private int getPosition() {
        if (mediaPlayer != null && isPrepared) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    private int getDuration() {
        if (mediaPlayer != null && isPrepared) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }


    private void playSongInPosition(int position) {
        if (position < 0 && position >= songList.size()) {
            return;
        }

        if (position == currentSongIndex) {
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(RetrofitMrg.baseUrl + songList.get(position).getResourceUrl());
                mediaPlayer.prepareAsync();
                currentSongIndex = position;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playNext() {
        int nextIndex = currentSongIndex + 1;
        if (songList.size() == 0) {
            return;
        } else {
            if (nextIndex <= songList.size()) {
                if (nextIndex == songList.size()) {
                    nextIndex = 0;
                }
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(RetrofitMrg.baseUrl + songList.get(nextIndex).getResourceUrl());
                        mediaPlayer.prepareAsync();
                        currentSongIndex = nextIndex;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    private void playLast() {
        int lastIndex = currentSongIndex - 1;
        if (songList.size() == 0) {
            return;
        } else {
            if (lastIndex >= 0) {
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(RetrofitMrg.baseUrl + songList.get(lastIndex).getResourceUrl());
                        mediaPlayer.prepareAsync();
                        isPrepared = false;
                        currentSongIndex = lastIndex;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case PLAY:  {
                    play();
                    return true;
                }
                case PAUSE: {
                    pause();
                    return true;
                }
                case PLAY_NEXT: {
                    playNext();
                    return true;
                }
                case PLAY_LAST: {
                    playLast();
                    return true;
                }
                case PLAY_STATUS: {
                    reply.writeInt(mediaPlayer.isPlaying() ? 1 : 0);
                    return true;
                }
                case SET_POSITION: {
                    setPosition(data.readInt());
                    return true;
                }
                case GET_POSITION: {
                    reply.writeInt(getPosition());
                    return true;
                }
                case MUSIC_PREPARED: {
                    if (isPrepared) {
                        reply.writeInt(1);
                    } else {
                        reply.writeInt(1);
                    }
                    return true;
                }
                case PLAY_EXACT_SONG: {
                    playSongInPosition(data.readInt());
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}

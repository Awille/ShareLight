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
import com.example.will.utils.TextUtils;

import java.io.IOException;
import java.util.List;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.GET_POSITION;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.MUSIC_PREPARED;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PAUSE;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_EXACT_SONG;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_LAST;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_NEXT;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_RECOMMEND_SONG;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.PLAY_STATUS;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.SET_INDEX_AND_LIST;
import static com.example.will.protocol.CommonConstant.MusicPlayAction.SET_POSITION;


//跨进程启动
public class MusicPlayService extends Service {

    private static final String TAG = "MusicPlayService";

    public MediaPlayer mediaPlayer = null;
    private IBinder mBinder = new MyBinder();

    public List<Song> songList;
    public int currentSongIndex = -1;

    private Song recommendSong;
    private Song lastRecommendSong = null;
    private int RECOMMEND_FLAG = 0;

    public static boolean isPrepared = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "startCommand" + this);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind ");
        currentSongIndex = -1;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        init(intent);
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "服务onCreate " + this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind " + this);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy " + this);
    }


    private void init(Intent intent) {
        if (mediaPlayer == null) {
            Log.e(TAG, "重新创建mediaPlayer");
            mediaPlayer = new MediaPlayer();
        }
        Log.e(TAG, " mediaPlayer.reset();");
        mediaPlayer.reset();
        setMediaPlayerListener();
    }

    private void initMediaPlayer(String resourceUrl) {
        try {
            mediaPlayer.setDataSource(resourceUrl);
            mediaPlayer.prepareAsync();
            isPrepared = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMediaPlayerListener() {
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                Bundle broadcastBundle = new Bundle();
                //音乐加载完成
                broadcastBundle.putString("CODE", "101");
                broadcastBundle.putBoolean("SAME_SONG", false);
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
                Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                if (RECOMMEND_FLAG == 0) {
                    Bundle broadcastBundle = new Bundle();
                    int nextIndex = currentSongIndex + 1;
                    if (nextIndex >= songList.size()) {
                        nextIndex = 0;
                    }
                    broadcastBundle.putString("NEXT_SONG_INDEX", String.valueOf(nextIndex));
                    broadcastBundle.putBoolean("RECOMMEND", false);
                    //歌单列表音乐播放完成
                    broadcastBundle.putString("CODE", "102");
                    broadcastIntent.putExtras(broadcastBundle);
                } else {
                    Bundle broadcastBundle = new Bundle();
                    broadcastBundle.putBoolean("RECOMMEND", true);
                    //推荐列表音乐播放完成
                    broadcastBundle.putString("CODE", "103");
                    broadcastIntent.putExtras(broadcastBundle);
                }
                sendBroadcast(broadcastIntent);
                Log.e(TAG, "播放完成");
            }
        });
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
            Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
            Bundle broadcastBundle = new Bundle();
            broadcastBundle.putBoolean("SAME_SONG", true);
            //播放列表位置歌曲相同
            broadcastBundle.putString("CODE", "104");
            broadcastIntent.putExtras(broadcastBundle);
            sendBroadcast(broadcastIntent);
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

    private void playRecommendSong() {
        if (recommendSong != null) {
            Log.e(TAG, "last recommend song :" + lastRecommendSong);
            if (lastRecommendSong != null && (lastRecommendSong.getSongId() == recommendSong.getSongId())) {
                Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                Bundle broadcastBundle = new Bundle();
                broadcastBundle.putBoolean("SAME_SONG", true);
                broadcastBundle.putInt("SONG_DURATION", mediaPlayer.getDuration());
                broadcastBundle.putString("CODE", "105");
                broadcastIntent.putExtras(broadcastBundle);
                //推荐歌曲相同
                sendBroadcast(broadcastIntent);
                return;
            }
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(RetrofitMrg.baseUrl + recommendSong.getResourceUrl());
                mediaPlayer.prepareAsync();
                isPrepared = false;
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                        Bundle broadcastBundle = new Bundle();
                        broadcastBundle.putBoolean("SAME_SONG", false);
                        broadcastBundle.putInt("SONG_DURATION", mediaPlayer.getDuration());
                        broadcastBundle.putString("CODE", "101");
                        broadcastIntent.putExtras(broadcastBundle);
                        sendBroadcast(broadcastIntent);
                        Log.e(TAG, "音乐加载完成");
                        isPrepared = true;
                        if (RECOMMEND_FLAG == 1) {
                            lastRecommendSong = recommendSong;
                        }
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Intent broadcastIntent = new Intent(CommonConstant.BroadcastName.MUSIC_PREPARED);
                        if (RECOMMEND_FLAG == 0) {
                            Bundle broadcastBundle = new Bundle();
                            int nextIndex = currentSongIndex + 1;
                            if (nextIndex >= songList.size()) {
                                nextIndex = 0;
                            }
                            broadcastBundle.putString("NEXT_SONG_INDEX", String.valueOf(nextIndex));
                            broadcastBundle.putBoolean("RECOMMEND", false);
                            broadcastBundle.putString("CODE", "102");
                            broadcastIntent.putExtras(broadcastBundle);
                        } else {
                            Bundle broadcastBundle = new Bundle();
                            broadcastBundle.putBoolean("RECOMMEND", true);
                            broadcastBundle.putString("CODE", "103");
                            broadcastIntent.putExtras(broadcastBundle);
                        }
                        sendBroadcast(broadcastIntent);
                        Log.e(TAG, "播放完成");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 106) {
                Log.e(TAG, "code : " + code);
            }
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
                    RECOMMEND_FLAG = 0;
                    lastRecommendSong = null;
                    playSongInPosition(data.readInt());
                    return true;
                }
                case PLAY_RECOMMEND_SONG: {
                    recommendSong = JSON.parseObject(data.readString(), Song.class);
                    RECOMMEND_FLAG = 1;
                    playRecommendSong();
                    return true;
                }
                case SET_INDEX_AND_LIST: {
                    songList = JSON.parseArray(data.readString(), Song.class);
                    int index = data.readInt();
                    RECOMMEND_FLAG = 0;
                    lastRecommendSong = null;
                    Log.e(TAG, "clear recommend song:" + lastRecommendSong);
                    playSongInPosition(index);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}

package com.example.will.sharelight.palyer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.will.utils.TextUtils;

public class PlayerBroadcast extends BroadcastReceiver {
    private static final String TAG = "PlayerBroadcast";

    public interface onBroadcastRecieveListener {
        void onSongPrepared(boolean isSame, int duration);
        void onSongFinished(int index);
    }

    public void setListener(onBroadcastRecieveListener listener) {
        this.listener = listener;
    }

    private onBroadcastRecieveListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String nextStr = bundle.getString("NEXT_SONG_INDEX");
        boolean recommend = bundle.getBoolean("RECOMMEND");
        boolean isSame = bundle.getBoolean("SAME_SONG");
        int duration = bundle.getInt("SONG_DURATION") / 1000;
        String code = bundle.getString("CODE");
        if (listener == null) {
            return;
        }
        if (code.equals("101")) {
            listener.onSongPrepared(isSame, duration);
        } else if (code.equals("102")) {
            listener.onSongFinished(Integer.valueOf(nextStr));
        } else if (code.equals("103")) {
            listener.onSongFinished(0);
        } else if (code.equals("104")) {
            listener.onSongPrepared(true, duration);
        } else if (code.equals("105")) {
            listener.onSongPrepared(true, duration);
        }
    }
}

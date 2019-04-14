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
        if (!TextUtils.isEmpty(nextStr)) {
            listener.onSongFinished(Integer.valueOf(nextStr));
        } else {
            boolean isSame = bundle.getBoolean("SAME_SONG");
            int duration = bundle.getInt("SONG_DURATION") / 1000;
            if (listener != null) {
                listener.onSongPrepared(isSame, duration);
            }
        }
    }
}

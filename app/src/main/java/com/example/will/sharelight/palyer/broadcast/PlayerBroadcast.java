package com.example.will.sharelight.palyer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PlayerBroadcast extends BroadcastReceiver {
    private static final String TAG = "PlayerBroadcast";

    public interface onBroadcastRecieveListener {
        void onSongPrepared(int duration);
    }

    public void setListener(onBroadcastRecieveListener listener) {
        this.listener = listener;
    }

    private onBroadcastRecieveListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int duration = bundle.getInt("SONG_DURATION") / 1000;
        if (listener != null) {
            listener.onSongPrepared(duration);
        }
    }
}

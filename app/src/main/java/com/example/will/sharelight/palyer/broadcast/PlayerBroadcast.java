package com.example.will.sharelight.palyer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PlayerBroadcast extends BroadcastReceiver {
    private static final String TAG = "PlayerBroadcast";

    public interface onBroadcastRecieveListener {
        void onSongPrepared();
    }

    public void setListener(onBroadcastRecieveListener listener) {
        this.listener = listener;
    }

    private onBroadcastRecieveListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onSongPrepared();
    }
}

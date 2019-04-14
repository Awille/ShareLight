package com.example.will.sharelight.palyer;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.will.protocol.CommonConstant;

public class GetProgressThread extends Thread {

    private static GetProgressThread INSTANCE;

    private Context context;
    private IBinder mBinder;
    private Handler mainHandler;

    private boolean isInterrupted = false;

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public GetProgressThread(Context context, IBinder mBinder, Handler mainHandler) {
        this.context = context;
        this.mBinder = mBinder;
        this.mainHandler = mainHandler;
    }

    public static GetProgressThread getINSTANCE(Context context, IBinder mBinder, Handler mainHandler) {
        if (INSTANCE == null) {
            synchronized (GetProgressThread.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GetProgressThread(context, mBinder, mainHandler);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        while (true) {
            if (isInterrupted) {
                INSTANCE = null;
                break;
            }
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                mBinder.transact(CommonConstant.MusicPlayAction.GET_POSITION, data, reply, 0);
                final int position = reply.readInt();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((PlayerActivity)context).getPlayFragmentAdapter().getmCurrentFragment().setSongProgress(position/1000);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                data.recycle();
                reply.recycle();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

package com.example.will.sharelight.palyer;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.will.protocol.CommonConstant;
import com.example.will.sharelight.main.square.SquareFragment;

public class GetProgressThread extends Thread {

    private static GetProgressThread INSTANCE;

    private Context context;
    private IBinder mBinder;

    private Handler mainHandler;

    private boolean isInterrupted = false;

    private SquareFragment squareFragment;

    private boolean fromMain = false;

    public void setFromMain(boolean fromMain) {
        this.fromMain = fromMain;
    }

    public void setmBinder(IBinder mBinder) {
        this.mBinder = mBinder;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    public void setSquareFragment(SquareFragment squareFragment) {
        this.squareFragment = squareFragment;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public static GetProgressThread getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (GetProgressThread.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GetProgressThread();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        while (true) {
            if (isInterrupted) {
                break;
            }
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                if (mBinder != null) {
                    mBinder.transact(CommonConstant.MusicPlayAction.GET_POSITION, data, reply, 0);
                    final int position = reply.readInt();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!fromMain) {
                                PlayFragment playFragment = ((PlayerActivity)context).getPlayFragmentAdapter().getmCurrentFragment();
                                if (playFragment != null) {
                                    playFragment.setSongProgress(position/1000);
                                }
                            } else {
                                if (squareFragment != null) {
                                    squareFragment.getRecommendFragmentAdapter().getCurrentFragment().setSongProgress(position/1000);
                                }
                            }
                        }
                    });
                }
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

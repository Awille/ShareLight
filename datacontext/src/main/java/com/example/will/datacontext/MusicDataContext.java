package com.example.will.datacontext;

import com.example.will.protocol.user.User;

public class MusicDataContext {
    private static final String TAG = "MusicDataContext";

    private User user;

    private static MusicDataContext INSTANCE;

    private MusicDataContext() {
        user = new User();
    }

    public static MusicDataContext getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (MusicDataContext.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MusicDataContext();
                }
            }
        }
        return INSTANCE;
    }

    public void clearData() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

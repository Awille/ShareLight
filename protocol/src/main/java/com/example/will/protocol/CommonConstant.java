package com.example.will.protocol;

public interface CommonConstant {
    interface NetWork {
        String SUCCESS = "0";
        String FAIL = "-1";
    }
    interface MusicPlayAction {
        int PLAY_STATUS = 100;
        int PLAY = 101;
        int PAUSE = 102;
        int PLAY_LAST = 103;
        int PLAY_NEXT = 104;
        int GET_POSITION = 106;
        int SET_POSITION = 107;
        int MUSIC_PREPARED = 108;
    }

    interface BroadcastName {
        String MUSIC_PREPARED = "com.example.will.sharelight.music.prepared";
    }
}

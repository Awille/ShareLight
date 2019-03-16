package com.example.will.musicprovider;

import com.example.will.commentdomain.CommentProviderImpl;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.comment.CommentProvider;
import com.example.will.protocol.song.SongProvider;
import com.example.will.protocol.songlist.SongListProvider;
import com.example.will.protocol.user.UserProvider;
import com.example.will.songdomain.SongProviderImpl;
import com.example.will.songlistdomain.SongListProviderImpl;
import com.example.will.userdomain.UserProviderImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MusicProvider {
    private static Map<String, BasicProvider> providerMap = new ConcurrentHashMap<>();


    private static MusicProvider INSTANCE;

    public static MusicProvider getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (MusicProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MusicProvider();
                }
            }
        }
        return INSTANCE;
    }

    private MusicProvider() {
        initProvider();
    }

    private void initProvider() {
        providerMap.put("user", new UserProviderImpl());
        providerMap.put("song", new SongProviderImpl());
        providerMap.put("songList", new SongListProviderImpl());
        providerMap.put("comment", new CommentProviderImpl());
    }

    public BasicProvider getProvider(String key) {
        return providerMap.get(key);
    }

    public UserProvider getUserProvider() {
        return (UserProvider) getProvider("user");
    }

    public SongProvider getSongProvider() {
        return (SongProvider) getProvider("song");
    }

    public SongListProvider getSongListProvider() {
        return (SongListProvider) getProvider("songList");
    }

    public CommentProvider getCommentProvider() {
        return (CommentProvider) getProvider("comment");
    }
}

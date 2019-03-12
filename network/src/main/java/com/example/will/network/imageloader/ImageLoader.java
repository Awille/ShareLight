package com.example.will.network.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.LruCache;

import com.example.will.network.imageloader.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageLoader {
    private static final String TAG = "ImageLoader";

    /**
     * 磁盘缓存大小
     */
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    public static final int MESSAGE_POST_RESULT = 1;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final long KEEP_ALIVE = 10L;

    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private static final int DISK_CACHE_INDEX = 0;

    private boolean mIsDiskLruCacheCreated = false;

    public static final ThreadFactory mThreadFactory = new ThreadFactory() {
        private AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    public static final Executor IMAGE_LOADER_THREAD_POOL = new ThreadPoolExecutor(
            CPU_COUNT,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            mThreadFactory);


    private Context mContext;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context) {
        initLruCache();
        initDiskLruCache(context);
    }

    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }


    private Bitmap loadBitmapFromMemoryCache(String url) {
        String key = hashKeyFromUrl(url);
        return getBitmapFromMemoryCache(key);
    }

    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(TAG, "it is not recommended to load bitmap in UI thread");
        }
        if (!mIsDiskLruCacheCreated || mDiskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = ImageResizer.decodeSampleBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }


    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
            e.printStackTrace();
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i ++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * Lru初始化 缓存大小为当前进程可用内存的1/8 单位为KB
     */
    private void initLruCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        //标明大小 还有重写size函数表示里面的每个存储单位的大小
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }




    private void initDiskLruCache(Context context) {
        mContext = context.getApplicationContext();
        File diskCacheDir = getDiskCacheDir(context, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUsableSize(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        Log.e(TAG, "DiskCache path:" + cachePath + File.pathSeparator + uniqueName);
        return new File(cachePath + File.pathSeparator + uniqueName);
    }

    private long getUsableSize(File path) {
        return path.getUsableSpace();
    }




}

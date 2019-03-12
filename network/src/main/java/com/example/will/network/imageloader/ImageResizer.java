package com.example.will.network.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return null;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.e(TAG, "origin width = " + width + ", height = " + height);
        if (width == 0 || height == 0) {
            return 1;
        }
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

        }
        return inSampleSize;
    }
}

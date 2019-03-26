package com.example.will.network.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public static Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public static Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd, int reqWidht, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidht, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static Bitmap decodeSampleBitmapFromStream(InputStream in, int reqWidth, int reqHeight) {
        //inputstream对象只能被读取一次
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream1 = null;
        InputStream inputStream2 = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            inputStream1 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            inputStream2 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap test = BitmapFactory.decodeStream(inputStream1, null, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(inputStream2, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (inputStream1 != null) {
                    inputStream1.close();
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算图片压缩比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 图片压缩倍数
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
            inSampleSize = 2;
            while (halfHeight > reqHeight && halfWidth > reqWidth) {
                halfHeight /= 2;
                halfWidth /= 2;
                inSampleSize *= 2;
            }
        }
        Log.e(TAG, "the inSampleSize =" + inSampleSize);
        return inSampleSize;
    }
}

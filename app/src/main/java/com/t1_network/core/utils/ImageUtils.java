package com.t1_network.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理工具
 *
 * @author vinge
 * @version 1.0
 */
public class ImageUtils {

    /**
     * 缩小图片并保存到文件中,返回一个OutputStream文件
     *
     * @param context
     * @param file
     * @param width
     * @param height
     * @param height
     * @return
     * @throws IOException
     */
    public static File ShrinkBitmap(Context context, File file, int width, int height) throws IOException {

        Bitmap bitmap = getImageThumbnail(file.getAbsolutePath(), width, height);

        File temp = new File(context.getCacheDir(), "taiyi-uploads");
        temp.mkdirs();
        String path = temp.getAbsolutePath() + File.separator + "IMG_Upload_" + new Date().getTime() + ".jpeg";
        File outFile = new File(path);

        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
            bitmap.compress(CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outFile;
    }

    public static Bitmap getImageThumbnail(final String pPath, final int pWidth, final int pHeight) {

		/* There isn't enough memory to open up more than a couple camera photos */
        /*没有足够的内存来打开超过两相机照片*/
        /* So pre-scale the target bitmap into which the file is decoded */
        /*所以缩放目标位图文件的解码*/
        /* Get the size of the ImageView */
        /*得到ImageView的大小*/
        int targetW = pWidth;
        int targetH = pHeight;

		/* Get the size of the image */
        /*得到image的大小*/
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        /*
            减少图片显示方式
		 */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        /*
        设置位图选项	扩展图片解码的目标
		 */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        /* JPEG文件解码成一个位图*/
        Bitmap bitmap = BitmapFactory.decodeFile(pPath, bmOptions);

        return bitmap;
    }

    /**
     * 缩小位图并将其保存到一个文件中,并返回您的outputstream文件
     *
     * @param context
     * @param inputStream
     * @param inSampleSize
     * @param quality
     * @return
     * @throws IOException
     */
    public static File ShrinkBitmap(Context context, InputStream inputStream, int inSampleSize, int quality) throws IOException {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);

        bmpFactoryOptions.inSampleSize = inSampleSize;

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);
        System.gc();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "WWP-Uploads" + File.separator + "compressed_" + new Random().nextInt(9999) + ".jpg";
        File outFile = new File(path);
        outFile.getParentFile().mkdirs();

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));

        bitmap.compress(CompressFormat.JPEG, quality, out);
        out.flush();
        out.close();
        System.gc();

        return outFile;
    }

    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        CompressFormat format = CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }


    /**
     * 保存 ImageView的图片,待测试
     *
     * @param imageView
     * @return
     */
    public boolean saveImageInImageView(ImageView imageView) {

        Bitmap bitmap = null;
        try {
            imageView.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);
            return saveBitmap2file(bitmap, "/sdcard/aaa/good.png");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }


}

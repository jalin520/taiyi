package com.t1_network.taiyi.model.factory;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.t1_network.taiyi.R;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/15 11:32
 * 修改记录:
 */
public class ImageOptionFactory {


    public static DisplayImageOptions getGoodOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_good)
                .showImageForEmptyUri(R.drawable.default_good)
                .showImageOnFail(R.drawable.default_good)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        return options;
    }


    public static DisplayImageOptions getBannerOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_banner)
                .showImageForEmptyUri(R.drawable.default_banner)
                .showImageOnFail(R.drawable.default_banner)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        return options;
    }

}

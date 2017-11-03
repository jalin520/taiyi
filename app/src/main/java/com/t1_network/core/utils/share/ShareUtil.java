package com.t1_network.core.utils.share;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by chenyu on 2015/12/3.
 */
public class ShareUtil {

    private static Context context;
    private static OnekeyShare onekeyShare;

    /**
     * 微信、新浪微博、QQ空间、QQ的分享字段
     */
    private static String text;
    private static String imagePath;
    private static String imageUrl;
    /**
     * QQ、QQ空间和微信的分享字段
     */
    private static String title;
    /**
     * QQ、QQ空间的分享字段，QQ空间必填
     */
    private static String titleUrl;
    /**
     * QQ 和微信分享的字段
     */
    private static String musicUrl;
    /**
     * QQ空间的分享字段，必填
     */
    private static String site;
    private static String siteUrl;
    /**
     * 新浪微博的分享字段，可选
     */
    private static float latitude;
    private static float longitude;
    /**
     * 微信的分享字段
     */
    private static int imageDataId;
    private static String url;
    private static String filePath;
    private static String extInfo;

    public static void share(String platform, final Context context, Share share) {

        text = share.getText();
        imagePath = share.getImagePath();
        imageUrl = share.getImageUrl();
        title = share.getTitle();
        titleUrl = share.getTitleUrl();
        musicUrl = share.getMusicUrl();
        site = share.getSite();
        siteUrl = share.getSiteUrl();
        latitude = share.getLatitude();
        longitude = share.getLongitude();
        imageDataId = share.getImageDataId();
        url = share.getUrl();
        filePath = share.getFilePath();
        extInfo = share.getExtInfo();

        onekeyShare = new OnekeyShare();

        if (text != null) {
            onekeyShare.setText(text);
        }
        if (imagePath != null) {
            onekeyShare.setImagePath(imagePath);
        }
        if (imageUrl != null) {
            onekeyShare.setImageUrl(imageUrl);
        }
        if (title != null) {
            onekeyShare.setTitle(title);
        }
        if (titleUrl != null) {
            onekeyShare.setTitleUrl(titleUrl);
        }
        if (musicUrl != null) {
            onekeyShare.setMusicUrl(musicUrl);
        }
        if (site != null) {
            onekeyShare.setSite(site);
        }
        if (siteUrl != null) {
            onekeyShare.setSiteUrl(siteUrl);
        }
        if (latitude != 0f) {
            onekeyShare.setLatitude(latitude);
        }
        if (longitude != 0f) {
            onekeyShare.setLongitude(longitude);
        }
        if (url != null) {
            onekeyShare.setUrl(url);
        }
        if (filePath != null) {
            onekeyShare.setFilePath(filePath);
        }

        /**
         * 为不同的平台添加不同的分享内容
         */
        onekeyShare.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                //微信平台
                if (Wechat.NAME.equals(platform.getName())) {
                    //当设置了imageData的分享内容
                    if (imageDataId != 0) {
                        paramsToShare.setTitle(title);
                        paramsToShare.setText(text);
                        paramsToShare.setImageData(BitmapFactory.decodeResource
                                (context.getResources(), imageDataId));
                        paramsToShare.setShareType(Platform.SHARE_IMAGE);
                        if (url != null) {
                            paramsToShare.setUrl(url);
                            paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                            if (url.endsWith(".flv") || url.endsWith(".swf") ||
                                    url.endsWith(".rm")) {
                                paramsToShare.setShareType(Platform.SHARE_VIDEO);
                            }
                            if (musicUrl != null) {
                                paramsToShare.setMusicUrl(musicUrl);
                                paramsToShare.setShareType(Platform.SHARE_MUSIC);
                            }
                        }
                        if (filePath != null) {
                            paramsToShare.setFilePath(filePath);
                            paramsToShare.setShareType(Platform.SHARE_FILE);
                            if (extInfo != null) {
                                paramsToShare.setExtInfo(extInfo);
                                paramsToShare.setShareType(Platform.SHARE_APPS);
                            }
                        }

                    }
                    //extInfo为应用信息脚本，不为Null时，分享类型为SHARE_APPS
                    if (extInfo != null) {
                        paramsToShare.setShareType(Platform.SHARE_APPS);
                        paramsToShare.setTitle(title);
                        paramsToShare.setText(text);
                        if (imagePath != null) {
                            paramsToShare.setImagePath(imagePath);
                        }
                        if (imageUrl != null) {
                            paramsToShare.setImageUrl(imageUrl);
                        }
                        if (imageDataId != 0) {
                            paramsToShare.setImageData(BitmapFactory.decodeResource
                                    (context.getResources(), imageDataId));
                        }
                        paramsToShare.setFilePath(filePath);
                        paramsToShare.setExtInfo(extInfo);
                    }
                }
            }
        });

        onekeyShare.setSilent(false);
        onekeyShare.setPlatform(platform);
        onekeyShare.setCallback(new OneKeyShareCallback());

        onekeyShare.show(context);
    }

    /**
     * 分享结果的回调
     */
    private static class OneKeyShareCallback implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }

}

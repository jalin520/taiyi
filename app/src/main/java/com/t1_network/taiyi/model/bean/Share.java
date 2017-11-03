package com.t1_network.taiyi.model.bean;

/**
 * Created by chenyu on 2015/12/1.
 */
public class Share {

    /**
     * 微信、新浪微博、QQ空间、QQ的分享字段
     */
    private String text;   //待分享的文本
    private String imagePath;  //待分享的本地图片。如果目标平台使用客户端分享，此路径不可以在/data/data下面
    private String imageUrl;   //待分享的网络图片

    /**
     * QQ、QQ空间和微信的分享字段
     */
    private String title;    //分享内容的标题

    /**
     * QQ、QQ空间的分享字段，QQ空间必填
     */
    private String titleUrl;    //分享内容标题的链接地址

    /**
     * QQ 和微信分享的字段
     */
    private String musicUrl;    //分享音频时的音频文件网络地址

    /**
     * QQ空间的分享字段，必填
     */
    private String site;    //QQ空间的字段，标记分享应用的名称
    private String siteUrl;   // QQ空间的字段，标记分享应用的网页地址

    /**
     * 新浪微博的分享字段，可选
     */
    private float latitude;  //分享位置的纬度，有效范围:-90.0到+90.0，+表示北纬
    private float longitude;  //分享位置的经度，longitude：有效范围：-180.0到+180.0，+表示东经

    /**
     * 微信的分享字段
     */
    private int imageDataId;  //各类分享内容中的图片bitmap对象资源ID，可以替代imagePatd或者imageUrl
    private String url;     //分享内容的url
    private String filePath;  //待分享的文件路径
    private String extInfo;    //分享应用时，可以选择分享二进制文件或者脚本，此字段用来设置分享应用中的脚本


    /**
     * ----------------------------分享到QQ好友，不同分享类型参数--------------------------------------
     *
     * |分享图文| title | titleUrl| text | imagePath |
     *                                  | imageUrl  |
     * |分享音乐| title | titleUrl| text | imagePath | musicUrl |
     *                                  | imageUrl  |
     * |分享到微博|               |text  | imagePath | isShareTencentWeibo  |
     *
     *
     * -----------------------------分享到QQ空间，不同分享类型参数-------------------------------------
     *
     * |分享文本| title | titleUrl | text |           | site | siteUrl |
     * |分享图文| title | titleUrl | text | imagePath | site | siteUrl |
     *                                   | imageUrl  |
     * |分享到腾讯微博|             | text | imagePath |      |isShareTencentWeibo |
     *                                   | imageUrl  |
     */


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public int getImageDataId() {
        return imageDataId;
    }

    public void setImageDataId(int imageDataId) {
        this.imageDataId = imageDataId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}

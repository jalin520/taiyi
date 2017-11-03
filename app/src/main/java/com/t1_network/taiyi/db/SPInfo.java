package com.t1_network.taiyi.db;

/**
 * 功能：SP的字段信息
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class SPInfo {

    /**
     * 是否第一次登陆，是为true，否为false
     */
    public static final String IS_FIRST_USE = "isFirstUse";

    /**
     * 记录用户的访问token
     */
    public static final String TOKEN = "TOKEN";


    /**
     * 搜索页的最近搜索
     */
    public static final String LAST_SEARCH = "LAST_SEARCH";

    /**
     * 对比的商品
     */
    public static final String COMPARE_GOOD = "COMPARE_GOOD";


}

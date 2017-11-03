package com.t1_network.taiyi.db;

import com.google.gson.Gson;
import com.t1_network.core.db.SPHelper;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.model.bean.good.GoodDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：具体到业务层面的SP封装，如是否第一次使用App等，如数据需要加密，请在此处加密解密
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class TYSP {

    /**
     * 是否第一次使用App
     */
    public static boolean isFirstUse() {
        return SPHelper.getBoolean(SPInfo.IS_FIRST_USE, true);
    }

    public static void setNotFirstUse() {
        SPHelper.save(SPInfo.IS_FIRST_USE, false);
    }

    /**
     * 用户的token
     */

    public static String getToken() {
        return SPHelper.getString(SPInfo.TOKEN);
    }

    public static boolean setToken(String accessToken) {
        try {
            SPHelper.save(SPInfo.TOKEN, accessToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void clearToken() {
        SPHelper.save(SPInfo.TOKEN, "");
    }


    /**
     * 搜索页的 最近搜索
     */

    public static void saveLastSearch(List<String> wordList) {

        try {
            //防止 wordList为空
            if (wordList == null) {
                wordList = new ArrayList<String>();
            }


            Map<String, Object> data = new HashMap<String, Object>();
            data.put(SPInfo.LAST_SEARCH, wordList);


            SPHelper.save(SPInfo.LAST_SEARCH, new Gson().toJson(data));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取热门搜索列表
     *
     * @return
     */
    public static List<String> getLastSearch() {

        try {
            String lastSearch = SPHelper.getString(SPInfo.LAST_SEARCH, (new JSONObject()).toString());

            JSONObject json = new JSONObject(lastSearch);

            LogUtils.e(json.toString());

            JSONArray array = json.getJSONArray(SPInfo.LAST_SEARCH);

            List<String> wordList = new ArrayList<String>();

            for (int i = 0; i < array.length(); i++) {
                wordList.add(array.getString(i));
            }

            return wordList;

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return new ArrayList<String>();
    }

    /**
     * 清除热门搜索列表
     */
    public static void clearLastSearch() {

        List<String> wordList = getLastSearch();
        wordList.clear();
        saveLastSearch(wordList);
    }

    /**
     * 添加热门搜索词
     *
     * @param word
     */
    public static void addLastSearch(String word) {


        List<String> wordList = getLastSearch();

        LogUtils.e("添加前:" + wordList.size());
        LogUtils.e(word);


        List<String> newWordList = new ArrayList<String>();
        newWordList.add(word);

        for (int i = 0; i < wordList.size() && newWordList.size() < 10; i++) {

            if (wordList.get(i).equals(word)) {
                continue;
            }

            newWordList.add(wordList.get(i));
        }

        saveLastSearch(newWordList);

        LogUtils.e("添加后:" + newWordList.size());
    }


    /**
     * @param goodDetailList
     */
    public static void saveCompareGood(List<GoodDetail> goodDetailList) {

        if (goodDetailList == null) {
            goodDetailList = new ArrayList<GoodDetail>();
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put(SPInfo.COMPARE_GOOD, goodDetailList);
        SPHelper.save(SPInfo.COMPARE_GOOD, new Gson().toJson(data));
    }

    public static void addCompareGood(GoodDetail goodDetail) {

        List<GoodDetail> goodList = getCompareGood();

        for (GoodDetail detail : goodList) {
            if (detail.getProduct()==null){
                System.out.println(detail.getProduct());
                return;
            }
            if (detail.getProduct().getId().equals(goodDetail.getProduct().getId())) {
                System.out.println(detail.getProduct());
                return;
            }
        }
        TipUtils.toast("商品添加成功");
        goodList.add(goodDetail);
        saveCompareGood(goodList);
    }

    public static void removeCompareGood(GoodDetail goodDetail) {

        if (goodDetail == null)
            return;


        List<GoodDetail> goodList = getCompareGood();
        if (goodList.contains(goodDetail)) {
            goodList.remove(goodDetail);
            saveCompareGood(goodList);
            return;
        }

    }

    public static List<GoodDetail> getCompareGood() {
        try {
            String str = SPHelper.getString(SPInfo.COMPARE_GOOD, (new JSONObject()).toString());

            JSONObject json = new JSONObject(str);

            JSONArray array = json.getJSONArray(SPInfo.COMPARE_GOOD);

            List<GoodDetail> goodList = new ArrayList<GoodDetail>();

            for (int i = 0; i < array.length(); i++) {

                JSONObject temp = array.getJSONObject(i);

                GoodDetail goodDetail = new Gson().fromJson(temp.toString(), GoodDetail.class);

                goodList.add(goodDetail);
            }

            return goodList;

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return new ArrayList<GoodDetail>();
    }


}

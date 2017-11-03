package com.t1_network.taiyi.net.api.home;

import com.google.gson.reflect.TypeToken;
import com.t1_network.core.app.Build;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.home.Activity;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/27 15:47
 * 修改记录:
 */
public class ActivityAPI extends TYAPI {

    private ActivityAPIListener listener;

    public ActivityAPI(ActivityAPIListener listener) {
        this.listener = listener;

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiActivityFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        JSONObject dataJson = null;
        try {
            dataJson = new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            listener.apiActivityFailure(0, ResUtils.getString(R.string.error_reslove));
        }

        List<Activity> actList1 = null;
        List<Activity> actList2 = null;
        List<Activity> actList3 = null;
        List<Activity> actList4 = null;
        List<Activity> actList5 = null;

        try {
            JSONArray module1 = dataJson.getJSONArray("1");
            actList1 = gson.fromJson(module1.toString(), new TypeToken<List<Activity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray module2 = dataJson.getJSONArray("2");
            actList2 = gson.fromJson(module2.toString(), new TypeToken<List<Activity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray module3 = dataJson.getJSONArray("3");
            actList3 = gson.fromJson(module3.toString(), new TypeToken<List<Activity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray module4 = dataJson.getJSONArray("4");
            actList4 = gson.fromJson(module4.toString(), new TypeToken<List<Activity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray module5 = dataJson.getJSONArray("5");
            actList5 = gson.fromJson(module5.toString(), new TypeToken<List<Activity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        listener.apiActivitySuccess(actList1, actList2, actList3, actList4, actList5);


    }

    @Override
    public String getURL() {
        return Build.HOST + "homepage/activity";
    }

    public interface ActivityAPIListener {

        public void apiActivitySuccess(List<Activity> module1, List<Activity> module2, List<Activity> module3, List<Activity> module4, List<Activity> module5);

        public void apiActivityFailure(long code, String msg);

    }
}

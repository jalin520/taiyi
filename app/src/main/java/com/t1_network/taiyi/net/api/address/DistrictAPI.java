package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.utils.FileUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 15/12/8.
 */
public class DistrictAPI {


    private DistrictAPIListener listener;

    public DistrictAPI(DistrictAPIListener listener, String province, String city) {
        this.listener = listener;

        List<String> districtList = new ArrayList<String>();

        String str = FileUtils.getFileForString("address.json");

        try {
            JSONObject json = new JSONObject(str);

            JSONObject cityJson = json.getJSONObject(province);
            JSONArray districtJson = cityJson.getJSONArray(city);

            for (int i = 0; i < districtJson.length(); i++) {
                districtList.add(districtJson.getString(i));
            }

            listener.apiDistrictSuccess(districtList);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiDistrictFailure(0, ResUtils.getString(R.string.error_get_data_error));
        }
    }

    public interface DistrictAPIListener {
        public void apiDistrictSuccess(List<String> districtList);

        public void apiDistrictFailure(long code, String msg);
    }

}

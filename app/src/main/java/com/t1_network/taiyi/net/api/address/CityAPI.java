package com.t1_network.taiyi.net.api.address;

import com.t1_network.core.utils.FileUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by David on 15/12/8.
 */
public class CityAPI {


    private CityAPIListener listener;

    public CityAPI(CityAPIListener listener, String province) {
        this.listener = listener;

        List<String> cityList = new ArrayList<String>();

        String str = FileUtils.getFileForString("address.json");

        try {
            JSONObject json = new JSONObject(str);

            JSONObject cityJson = json.getJSONObject(province);

            Iterator<String> it = cityJson.keys();

            while (it.hasNext()) {
                cityList.add(it.next());
            }

            listener.apiCitySuccess(cityList);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiCityFailure(0, ResUtils.getString(R.string.error_get_data_error));
        }
    }

    public interface CityAPIListener {
        public void apiCitySuccess(List<String> cityList);

        public void apiCityFailure(long code, String msg);
    }

}

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
public class ProvinceAPI {

    private ProvinceAPIListener listener;

    public ProvinceAPI(ProvinceAPIListener listener) {
        this.listener = listener;

        List<String> provinceList = new ArrayList<String>();

        String str = FileUtils.getFileForString("address.json");

        try {
            JSONObject json = new JSONObject(str);

            Iterator<String> it = json.keys();

            while (it.hasNext()) {
                provinceList.add(it.next());
            }

            listener.apiProvinceSuccess(provinceList);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.apiProvinceFailure(0, ResUtils.getString(R.string.error_get_data_error));
        }
    }

    public interface ProvinceAPIListener {
        public void apiProvinceSuccess(List<String> provinceList);

        public void apiProvinceFailure(long code, String msg);
    }

}

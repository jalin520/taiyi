package com.t1_network.taiyi.net;


import com.t1_network.core.app.App;
import com.t1_network.core.net.BasicAPI;
import com.t1_network.core.utils.IPUtils;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.User;

/**
 * Created by David on 15/12/16.
 */
public abstract class TYAPI extends BasicAPI {


    public TYAPI() {

        header.put("ip", IPUtils.getIP());
        header.put("access", "21");
        header.put("authorization", "5c696f4a8ede40a69b7a3b23e2dca3dd");

    }

    @Override
    public void addUserAuthorization() {
        try {
            if (App.getApp().isLogin()) {
                User user = App.getApp().getUser();
                header.put("user", JsonUtils.packageString(user.getLogin().getId(), user.getLogin().getUuid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiRequestError(0, ResUtils.getString(R.string.error_user_is_unlogin));
        }
    }

    @Override
    public void requestSuccess(String data) {
        apiRequestSuccess(data);
    }

    @Override
    public void requestError(long code, String msg) {
        apiRequestError(code, msg);
    }

    public abstract void apiRequestSuccess(String data);

    public abstract void apiRequestError(long code, String msg);
}

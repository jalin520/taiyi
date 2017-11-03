package com.t1_network.taiyi.net.api.user;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/2/17.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class UserInfoAPI extends TYAPI {

    private UserInfoAPIListener mListener;

    public UserInfoAPI(UserInfoAPIListener listener,String nickname,String sex,String birthday , String photo) {
        this.mListener = listener;
        addParams("photo",photo);
        addParams("nickname",nickname);
        addParams("sex",sex);
        addParams("birthday",birthday);
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        mListener.apiUserInfoSuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiUserInfoFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST+"consumer/fields";
    }

    public interface UserInfoAPIListener{
        void apiUserInfoSuccess();
        void apiUserInfoFailure(long code, String msg);
    }




}

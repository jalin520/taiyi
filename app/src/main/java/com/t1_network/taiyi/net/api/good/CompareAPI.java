package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.CompareBean;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/20.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CompareAPI extends TYAPI{

    private CompareAPIListener mListener;

    public CompareAPI(CompareAPIListener mListener,String src,String dest) {
        this.mListener = mListener;
        addParams("dest",dest);
        addParams("src",src);

        new TYHttp(this).request();
    }

    @Override
    public void apiRequestSuccess(String data) {
        CompareBean compareBean = gson.fromJson(data, CompareBean.class);
        mListener.apiCompareAPISuccess(compareBean);

    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.apiCompareAPIFailure(code,msg);
    }

    @Override
    public String getURL() {
        return Build.HOST+"compare/detail";
    }

    public interface CompareAPIListener {
        void apiCompareAPISuccess(CompareBean compareBean);
        void apiCompareAPIFailure(long code, String msg);
    }
}

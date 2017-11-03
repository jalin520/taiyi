package com.t1_network.taiyi.net.api.voucher;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.Voucher;
import com.t1_network.taiyi.model.bean.VoucherList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能：我的代币接口
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-11-16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public class VoucherAPI extends TYAPI {

    private VoucherAPIListener listener;

    public VoucherAPI(VoucherAPIListener listener, long limit) {
        this.listener = listener;
        addUserAuthorization();

        addParams("pagenum", limit + "");
        addParams("pagesize", "10");

        new TYHttp(this).request();

    }

    @Override
    public String getURL() {
        return Build.HOST + "voucher/list";
    }

    @Override
    public void apiRequestSuccess(String data) {
        VoucherList voucherList = gson.fromJson(data.toString(), VoucherList.class);
        listener.apiVoucherSuccess(voucherList.getVoucherList());
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiVoucherFailure(code, msg);
    }

    public interface VoucherAPIListener {
        public void apiVoucherSuccess(List<Voucher> voucherList);

        public void apiVoucherFailure(long code, String msg);
    }

}

package com.t1_network.taiyi.net.api.home;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.home.Classfication;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能: 获取商品分类列表
 * 创建者: David
 * 创建日期: 15/12/22 14:46
 * 修改记录:
 */
public class ClassficationAPI extends TYAPI {

    private ClassficationAPIListener listener;

    public ClassficationAPI(ClassficationAPIListener listener) {
        this.listener = listener;
        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiClassficationFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        List<Classfication> classficationList = Classfication.resloveClassfication(data);
        listener.apiClassficationSuccess(classficationList);
    }

    @Override
    public String getURL() {
        return Build.HOST + "category/category";
    }

    public interface ClassficationAPIListener {

        public void apiClassficationSuccess(List<Classfication> classficationList);

        public void apiClassficationFailure(long code, String msg);

    }

}

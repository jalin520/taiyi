package com.t1_network.taiyi.net.api.order;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.order.ReturnOrderBean;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

/**
 * Created by David on 2016/1/26.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderReturnAPI extends TYAPI {

    /**
     * "orderid": "1511110301",
     * "productid": "10153",
     * "userid": "201511030001",
     * "type": "1",
     * "freight": "10",
     * "quantity": "1",
     * "refund": "0.01",
     * "name": "何健荣",
     * "phone": "15916210842",
     * "address": "广东省 珠海市 香洲区 唐家湾镇 哈工大路1号博士楼C206",
     * "reason": "不想要",
     * "explain": "坏了",
     */
    private OnOrderReturnAPIListener mListener;

    public OrderReturnAPI(OnOrderReturnAPIListener listener, ReturnOrderBean bean,String refund) {
        mListener = listener;
        addParams("orderid", bean.getOrderid());
        addParams("productid", bean.getProductid());
        addParams("type", bean.getType());
        addParams("freight", bean.getFreight());
        addParams("quantity", bean.getQuantity());
        addParams("refund", refund);
        addParams("name", bean.getName());
        addParams("phone", bean.getPhone());
        addParams("address", bean.getAddress());
        addParams("reason", bean.getReason());
        addParams("pic", bean.getProof());

        new TYHttp(this).request();
    }

    public interface OnOrderReturnAPIListener {
        void onOrderReturnAPISuccess();

        void onOrderReturnAPIFailure(long code, String msg);
    }


    @Override
    public void apiRequestSuccess(String data) {
        mListener.onOrderReturnAPISuccess();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        mListener.onOrderReturnAPIFailure(code, msg);
    }

    @Override
    public String getURL() {
        return Build.HOST + "orderback/commit";
    }


}

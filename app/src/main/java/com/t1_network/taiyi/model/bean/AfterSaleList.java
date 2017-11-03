package com.t1_network.taiyi.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 2016/1/11.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AfterSaleList {

    @SerializedName("result")
    private List<AfterSale> mSaleList;

    public List<AfterSale> getSaleList() {
        return mSaleList;
    }

    public void setSaleList(List<AfterSale> saleList) {
        mSaleList = saleList;
    }
}

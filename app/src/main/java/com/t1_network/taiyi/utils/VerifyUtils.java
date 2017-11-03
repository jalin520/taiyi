package com.t1_network.taiyi.utils;

import com.t1_network.taiyi.model.bean.AfterSale;
import com.t1_network.taiyi.model.bean.cart.GoodInCart;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.bean.good.Product;
import com.t1_network.taiyi.model.bean.order.GoodInOrder;
import com.t1_network.taiyi.model.bean.order.OrderDetailBean;
import com.t1_network.taiyi.model.bean.order.OrderWaitComment;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/20 16:35
 * 修改记录:
 */
public class VerifyUtils {


    public static boolean hasImage(Good good) {

        if (good == null) {
            return false;
        }

        if (good.getImageList() == null) {
            return false;
        }

        if (good.getImageList().size() == 0) {
            return false;
        }

        if (good.getImageList().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }

    public static boolean hasImage(GoodInOrder good) {

        if (good == null) {
            return false;
        }

        if (good.getImageList() == null) {
            return false;
        }

        if (good.getImageList().size() == 0) {
            return false;
        }

        if (good.getImageList().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }

    public static boolean hasImage(GoodInCart good) {

        if (good == null) {
            return false;
        }

        if (good.getImageList() == null) {
            return false;
        }

        if (good.getImageList().size() == 0) {
            return false;
        }

        if (good.getImageList().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }

    public static boolean hasImage(Product product) {
        if (product == null) {
            return false;
        }

        if (product.getGoodImageList() == null) {
            return false;
        }

        if (product.getGoodImageList().size() == 0) {
            return false;
        }

        if (product.getGoodImageList().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }

    public static boolean hasImage(OrderDetailBean.OrderEntity.OrderdetailEntity orderdetailEntity) {
        if (orderdetailEntity == null) {
            return false;
        }

        if (orderdetailEntity.getProductpic() == null) {
            return false;
        }

        if (orderdetailEntity.getProductpic().size() == 0) {
            return false;
        }

        if (orderdetailEntity.getProductpic().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }

    public static boolean hasImage(AfterSale afterSale) {
        if (afterSale == null) {
            return false;
        }

        if (afterSale.getProductpic() == null) {
            return false;
        }

        if (afterSale.getProductpic().size() == 0) {
            return false;
        }

        if (afterSale.getProductpic().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }


    public static boolean hasImage(OrderWaitComment orderWaitComment) {
        if (orderWaitComment == null) {
            return false;
        }

        if (orderWaitComment.getImageList() == null) {
            return false;
        }

        if (orderWaitComment.getImageList().size() == 0) {
            return false;
        }

        if (orderWaitComment.getImageList().get(0).getUrl() == null) {
            return false;
        }

        return true;
    }




}

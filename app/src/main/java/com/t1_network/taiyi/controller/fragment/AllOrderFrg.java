package com.t1_network.taiyi.controller.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by David on 2015/11/16.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AllOrderFrg extends BasicFrg{

    @Bind(R.id.frg_all_order_rv)
    RecyclerView rv_all;

    private List<Order> mDatas;

    public AllOrderFrg() {
        super(R.layout.frg_all_order);
    }

    @Override
    public void initView(View view) {
        addData();

//        OrderWaitPayAdapter adapter = new OrderWaitPayAdapter(mDatas);

//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        rv_all.setLayoutManager(manager);
//        rv_all.setAdapter(adapter);
    }

    private void addData() {
        mDatas = getList();
    }

    public List<Order> getList() {
        List<Order> orderList = new ArrayList<Order>();

        Order order = null;

//        order = new Order();
//        order.setId("15102217921");
//        order.setStatus("0");
//        order.setQuantity("5");
//        order.setTotal("1588.00");
//        order.setCommittime("2015-11-12 14:30:38");
//        order.setCancletime("5小时49分30秒");
//        orderList.add(order);
//
//        order = new Order();
//        order.setId("15102217921");
//        order.setStatus("0");
//        order.setQuantity("5");
//        order.setTotal("1588.00");
//        order.setCommittime("2015-11-12 14:30:38");
//        order.setCancletime("5小时49分30秒");
//        orderList.add(order);
//
//        order = new Order();
//        order.setId("15102217921");
//        order.setStatus("0");
//        order.setQuantity("5");
//        order.setTotal("1588.00");
//        order.setCommittime("2015-11-12 14:30:38");
//        order.setCancletime("5小时49分30秒");
//        orderList.add(order);

        return orderList;
    }



}

package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.AddressAdapter;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.net.api.address.DelAddressAPI;
import com.t1_network.taiyi.net.api.user.AddressAPI;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.walink.pulltorefresh.library.PullToRefreshBase;
import cn.walink.pulltorefresh.library.PullToRefreshListView;

public class AddressAct extends BasicAct implements PullToRefreshBase.OnRefreshListener<ListView>, AddressAPI.AddressAPIListener, DelAddressAPI.DelAddressAPIListener {


    @Bind(R.id.pull_to_refresh_root)
    PullToRefreshListView layoutRefresh;
    private ListView recyclerView;

    private AddressAdapter adapter;

    public AddressAct() {
        super(R.layout.act_address, R.string.title_activity_address);
    }

    public static final int RC_SELECT_ADDRESS = 1;
    public static final String P_ADDRESS = "P_ADDRESS";
    public static final String P_FROM = "P_FROM";

    private static final int FROM_ORDER = 1;
    private static final int FROM_USER_INFO = 2;
    private int from = FROM_USER_INFO;

    public static void startActivityFromOrder(Activity context) {
        Intent intent = new Intent(context, AddressAct.class);
        intent.putExtra(P_FROM, FROM_ORDER);
        context.startActivityForResult(intent, RC_SELECT_ADDRESS);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddressAct.class);
        intent.putExtra(P_FROM, FROM_USER_INFO);
        context.startActivity(intent);
    }


    @Override
    public void initView() {

        recyclerView = layoutRefresh.getRefreshableView();
        layoutRefresh.setOnRefreshListener(this);

        adapter = new AddressAdapter(null, this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();

        from = intent.getIntExtra(P_FROM, FROM_USER_INFO);

        if (from == FROM_ORDER) {
            recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent data = new Intent();
                    data.putExtra(P_ADDRESS, (Address) adapter.getData().get(position - 1));
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            });
        }


        layoutRefresh.setRefreshing(true);
        onRefresh(layoutRefresh);

    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        new AddressAPI(this);
    }


    private List<Address> data;

    @Override
    public void apiAddressSuccess(List<Address> addressList) {
        data = addressList;
        adapter.setData(addressList);
        layoutRefresh.onRefreshComplete();
    }

    @Override
    public void apiAddressFailure(long code, String msg) {
        showTip(msg);
        layoutRefresh.onRefreshComplete();
    }

    @OnClick(R.id.act_consignee_address_menu_add)
    public void addAddress() {
        AddressEditAct.startActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            layoutRefresh.setRefreshing(true);
            onRefresh(layoutRefresh);

            switch (requestCode) {

                case AddressEditAct.RC_ADD_ADDRESS:
                    showTip(ResUtils.getString(R.string.act_address_edit_tip_add_success));
                case AddressEditAct.RC_UPDATE_ADDRESS:
                    showTip(ResUtils.getString(R.string.act_address_edit_tip_update_success));
                    break;
            }
        }
    }

    @Override
    public void apiDelAddressSuccess() {
        showTip(ResUtils.getString(R.string.act_address_edit_tip_del_success));
    }

    @Override
    public void apiDelAddressFailure(long code, String msg) {
        showTip(msg);
    }
}

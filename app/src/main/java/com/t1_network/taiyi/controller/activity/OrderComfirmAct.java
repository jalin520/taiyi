package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.SpanStringUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.reveiver.UserUpdateReceiver;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.model.bean.cart.Cart;
import com.t1_network.taiyi.model.bean.cart.GoodInCart;
import com.t1_network.taiyi.model.bean.order.Order;
import com.t1_network.taiyi.model.bean.order.Receipt;
import com.t1_network.taiyi.model.event.UpdateCartEvent;
import com.t1_network.taiyi.model.event.UpdateUserEvent;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.net.api.address.DefaultAddressAPI;
import com.t1_network.taiyi.net.api.order.OrderComfirmAPI;
import com.t1_network.taiyi.net.api.shopcart.GetSelectCartAPI;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class OrderComfirmAct extends BasicAct implements GetSelectCartAPI.GetSelectCartAPIListener, OrderComfirmAPI.OrderComfirmAPIListener, DefaultAddressAPI.DefaultAddressAPIListener {

    /**
     * 没选择地址时显示
     */
    @Bind(R.id.comfirm_order_layout_please_select_address_no_address)
    RelativeLayout layoutNoAddress;

    /**
     * 选择地址后，显示地址的layout
     */
    @Bind(R.id.comfirm_order_layout_please_select_address_has_address)
    RelativeLayout layoutHasAddress;


    @Bind(R.id.comfirm_order_layout_good)
    LinearLayout layoutGood;

    @Bind(R.id.act_order_comfirm_text_total)
    TextView textTotal;

    @Bind(R.id.act_order_comfirm_text_good_count)
    TextView textCount;

    @Bind(R.id.layout_submit)
    RelativeLayout layoutSubmit;

    public OrderComfirmAct() {
        super(R.layout.act_order_comfirm, R.string.title_activity_order_comfirm);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OrderComfirmAct.class);
        context.startActivity(intent);
    }

    private boolean hasGetCart = false;
    private boolean hasGetAddress = false;

    private void isLoadingFinish() {
        if (hasGetCart && hasGetAddress) {
            dialog.dismiss();
            layoutSubmit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        dialog.show();

        new GetSelectCartAPI(this);
        new DefaultAddressAPI(this);

        String allToken = App.getApp().getUser().getConsumer().getVoucher();

        try {
            allTokenLeft = Long.parseLong(allToken);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            allTokenLeft = 0;
        }
    }

    private Cart cart;

    private List<GoodInCart> goodInCartList;

    private List<EditText> editVoucherList;
    private List<String> voucherList;

    private List<TextView> textVoucherList;

    @Override
    public void apiGetSelectCartSuccess(Cart cart) {

        this.cart = cart;

        String total = ResUtils.getString(R.string.comfirm_order_act_text_all_money, cart.getAllSelectMoney());
        String span = ResUtils.getString(R.string.comfirm_order_act_text_all_money_span, cart.getAllSelectMoney());
        CharSequence c = SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.text_orange), total, span);

        textTotal.setText(c);
        textCount.setText(ResUtils.getString(R.string.act_order_comfirm_text_good_count, cart.getAllSelectCount(), "0"));

        voucherList = new ArrayList<String>();
        editVoucherList = new ArrayList<EditText>();
        textVoucherList = new ArrayList<TextView>();
        editVoucherList.clear();

        for (int i = 0; i < cart.getGoodList().size(); i++) {

            GoodInCart good = cart.getGoodList().get(i);

            View view = getLayoutInflater().inflate(R.layout.item_good_in_order, null);
            TextView textName = (TextView) view.findViewById(R.id.orderName);
            TextView textPrice = (TextView) view.findViewById(R.id.order_is_seven);
            TextView textCount = (TextView) view.findViewById(R.id.orderAmount);
            ImageView imageGood = (ImageView) view.findViewById(R.id.item_good_in_order_image_good);

            TextView textVoucherTip = (TextView) view.findViewById(R.id.item_good_in_order_tv_coin);
            TextView textVoucher = (TextView) view.findViewById(R.id.item_good_in_order_text_voucher);
            TextView textTotal = (TextView) view.findViewById(R.id.item_good_in_order_text_total);

            EditText editVoucher = (EditText) view.findViewById(R.id.item_good_in_order_edit_voucher);

            textName.setText(good.getName());
            textPrice.setText(ResUtils.getString(R.string.money, good.getPrice()));
            textCount.setText(ResUtils.getString(R.string.count, good.getCount()));
            textTotal.setText(ResUtils.getString(R.string.money, good.getTotal()));

            textVoucherTip.setText(ResUtils.getString(R.string.item_good_in_order_text_voucher_tip, good.getVoucher()));
            textVoucher.setText(ResUtils.getString(R.string.item_good_in_order_text_voucher, App.getApp().getUser().getConsumer().getVoucher(), "0"));


            textVoucherList.add(textVoucher);


            if (VerifyUtils.hasImage(good)) {
                imageLoader.displayImage(good.getImageList().get(0).getUrl(), imageGood, ImageOptionFactory.getGoodOptions());
            }

            editVoucher.addTextChangedListener(new VoucherTextWatcher(editVoucher, good.getVoucher()));

            editVoucherList.add(editVoucher);
            layoutGood.addView(view);
        }

        hasGetCart = true;
        isLoadingFinish();
    }


    public long allTokenLeft = 0;

    private void initToken() {

        long sumToken = 0;

        for (int i = 0; i < editVoucherList.size(); i++) {
            String str = editVoucherList.get(i).getText().toString();
            if ("".equals(str)) {
                str = "0";
            }
            sumToken = sumToken + Long.parseLong(str);
        }

        String allToken = App.getApp().getUser().getConsumer().getVoucher();

        try {
            allTokenLeft = Long.parseLong(allToken) - sumToken;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            allTokenLeft = 0;
        }

        for (int i = 0; i < textVoucherList.size(); i++) {
            textVoucherList.get(i).setText(ResUtils.getString(R.string.item_good_in_order_text_voucher, allToken, sumToken + ""));
        }

        double allMoney = Double.parseDouble(cart.getAllSelectMoney());

        String total = ResUtils.getString(R.string.comfirm_order_act_text_all_money, (allMoney - sumToken) + "");
        String span = ResUtils.getString(R.string.comfirm_order_act_text_all_money_span, (allMoney - sumToken) + "");
        CharSequence c = SpanStringUtils.getHighLightText(ResUtils.getColor(R.color.text_orange), total, span);

        textTotal.setText(c);
    }


    public class VoucherTextWatcher implements TextWatcher {

        private long limit;

        private boolean flag = true;

        private EditText edit;

        private String str;

        private long pre = 0;

        public VoucherTextWatcher(EditText edit, String limit) {
            this.edit = edit;
            try {

                if ("0".equals(limit)) {
                    this.limit = 0;
                } else {
                    this.limit = Long.parseLong(limit);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                this.limit = 0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            LogUtils.e("afterTextChanged:" + s.toString() + " flag:" + flag);

            if (!flag) {
                flag = true;
                String str = s.toString().substring(0, s.length() - 1);
                edit.setText(str);
                edit.setSelection(edit.getText().toString().length());
            }

            initToken();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            str = s + "";

            if ("".equals(str)) {
                str = "0";
            }

            pre = Long.parseLong(str);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            str = s + "";

            if ("".equals(str)) {
                str = "0";
            }
            LogUtils.e("str:" + str + " limit:" + limit + " all:" + allTokenLeft);

            long input = Long.parseLong(str);

            double orderMoney = Double.parseDouble(cart.getAllSelectMoney());


            if (input > limit || input > allTokenLeft + pre || input >= orderMoney) {
                LogUtils.e("input:" + input);
                flag = false;
            }
        }
    }

    @Override
    public void apiGetSelectCartFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiOrderComfirmSuccess(String orderId) {

        EventBus.getDefault().post(new UpdateCartEvent(null)); //更新购物车事件
        EventBus.getDefault().post(new UpdateUserEvent()); //提交订单事件

        UserUpdateReceiver.send(this);
        Order order = new Order();
        order.setId(orderId);
        PayAct.startActivity(this, order);
        dialog.dismiss();
        finish();
    }

    @Override
    public void apiOrderComfirmFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }

    @OnClick(R.id.comfirm_order_act_btn_sumit_order)
    public void submit() {
        voucherList.clear();

        for (int i = 0; i < editVoucherList.size(); i++) {

            String voucher = editVoucherList.get(i).getText().toString();

            if (TextUtils.isEmpty(voucher)) {
                voucherList.add("0");
            } else {
                voucherList.add(voucher);
            }
        }
        dialog.show();
        new OrderComfirmAPI(this, address, receipt, cart.getGoodList(), voucherList);
    }

    @Override
    public void apiDefaultAddressSuccess(Address address) {
        this.address = address;
        initAddress(address);
        hasGetAddress = true;
        isLoadingFinish();

    }

    @Override
    public void apiDefaultAddressFailure(long code, String msg) {
        showTip(msg);
    }

    @OnClick(R.id.comfirm_order_layout_please_select_address_no_address)
    public void selectAddress() {
        AddressAct.startActivityFromOrder(this);
    }


    @Bind(R.id.comfirm_order_text_phone)
    TextView textMobile;
    @Bind(R.id.comfirm_order_text_address)
    TextView textAddress;
    @Bind(R.id.comfirm_order_text_consignee)
    TextView textPeople;

    private void initAddress(Address address) {

        if (address == null) {
            layoutNoAddress.setVisibility(View.VISIBLE);
            layoutHasAddress.setVisibility(View.GONE);
        } else {
            layoutHasAddress.setVisibility(View.VISIBLE);
            layoutNoAddress.setVisibility(View.GONE);
            textMobile.setText(address.getPhone());
            textAddress.setText(address.getProvince() + address.getCity() + address.getDistrict() + address.getAddress());
            textPeople.setText(ResUtils.getString(R.string.comfirm_order_act_text_consignee, address.getName()));
        }
    }


    @Bind(R.id.act_order_comfirm_text_invoice)
    TextView textInvoice;

    private void initInvoice(Receipt receipt) {

        if (receipt == null) {
            textInvoice.setText("不需要发票");
            return;
        }

        if (receipt.isPersonal()) {
            textInvoice.setText(receipt.getType() + ";个人;" + receipt.getContent());
        } else {
            textInvoice.setText(receipt.getType() + ";单位;" + receipt.getContent());
        }

    }


    @OnClick(R.id.comfirm_order_tv_invoice)
    public void toBillAct() {
        InvoiceAct.startActivity(this);
    }

    @OnClick(R.id.comfirm_order_layout_please_select_address_has_address)
    public void onClickaddress(View v) {
        AddressAct.startActivityFromOrder(this);
    }

    @OnClick(R.id.order_comfrim_layout_address)
    public void onClickLayout(View v) {
        KeyBoardUtils.hideKeyboard(this, layoutGood);
    }


    private Address address;

    private Receipt receipt = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {

                case AddressAct.RC_SELECT_ADDRESS:

                    if (data != null) {
                        address = (Address) data.getParcelableExtra(AddressAct.P_ADDRESS);
                        initAddress(address);
                    }

                    break;
                case InvoiceAct.RC_SELECT_INVOICE:

                    if (data != null) {
                        receipt = (Receipt) data.getParcelableExtra(InvoiceAct.P_RECEIPT);
                        initInvoice(receipt);
                    }
                    break;

            }
        }
    }
}

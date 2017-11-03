package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Address;
import com.t1_network.taiyi.model.verify.AddressVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.address.AddAddressAPI;
import com.t1_network.taiyi.net.api.address.LocationAPI;
import com.t1_network.taiyi.net.api.address.UpdateAddressAPI;
import com.t1_network.taiyi.widget.Switch.SwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

public class AddressEditAct extends BasicAct implements AddAddressAPI.AddAddressAPIListener,
        UpdateAddressAPI.UpdateAddressAPIListener,LocationAPI.LocationAPIListener {

    private String province = "";
    private String city = "";
    private String district = "";


    @Bind(R.id.act_address_text_province_city_district)
    TextView textDistrct;

    @Bind(R.id.act_address_edit_switch_default_address)
    SwitchButton switchButton;

    public AddressEditAct() {
        super(R.layout.act_address_edit, R.string.title_activity_address_edit);
    }


    public final static String P_CITY = "P_CITY";
    public final static String P_PROVINCE = "P_PROVINCE";
    public final static String P_DISTRICT = "P_DISTRICT";
    public final static String P_TYPE = "P_TYPE";
    public final static String P_ADDRESS = "P_ADDRESS";

    public final static int TYPE_ADD = 1;
    public final static int TYPE_UPDATE = 2;
    public int type;

    public final static int RC_ADD_ADDRESS = 3;
    public final static int RC_UPDATE_ADDRESS = 4;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, AddressEditAct.class);
        intent.putExtra(P_TYPE, TYPE_ADD);
        context.startActivityForResult(intent, RC_ADD_ADDRESS);
    }

    public static void startActivity(Activity context, Address address) {
        Intent intent = new Intent(context, AddressEditAct.class);
        intent.putExtra(P_TYPE, TYPE_UPDATE);
        intent.putExtra(P_ADDRESS, address);
        context.startActivityForResult(intent, RC_UPDATE_ADDRESS);
    }

    public static void startActivity(Context context, String province, String city, String district) {

        Intent intent = new Intent(context, AddressEditAct.class);
        intent.putExtra(P_PROVINCE, province);
        intent.putExtra(P_CITY, city);
        intent.putExtra(P_DISTRICT, district);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        province = intent.getStringExtra(P_PROVINCE);
        city = intent.getStringExtra(P_CITY);
        district = intent.getStringExtra(P_DISTRICT);
        textDistrct.setText(province + " " + city + " " + district);
    }

    private Address address;

    @Override
    public void initView() {

        Intent intent = getIntent();

        type = intent.getIntExtra(P_TYPE, TYPE_ADD);

        switch (type) {
            case TYPE_ADD:
                address = new Address();
                switchButton.setChecked(true);
                break;
            case TYPE_UPDATE:
                address = (Address) intent.getParcelableExtra(P_ADDRESS);
                editName.setText(address.getName());
                editPhone.setText(address.getPhone());
                editAddress.setText(address.getAddress());
                editTip.setText(address.getTag());
                textDistrct.setText(address.getProvince() + " " + address.getCity() + " " + address.getDistrict());
                province = address.getProvince();
                city = address.getCity();
                district = address.getDistrict();
                switchButton.setChecked("1".equals(address.getDefaults()));
                break;
        }
    }

    @OnClick(R.id.act_address_layout_province_city_district)
    public void selectDistrict() {
        SelectProvinceAct.startActivity(this);
    }

    @Bind(R.id.act_address_edit_name)
    EditText editName;
    @Bind(R.id.act_address_edit_mobile)
    EditText editPhone;
    @Bind(R.id.act_address_edit_address)
    EditText editAddress;
    @Bind(R.id.act_address_edit_tip)
    EditText editTip;

    @OnClick(R.id.act_consignee_address_menu_save)
    public void save() {

        address.setName(editName.getText().toString());
        address.setPhone(editPhone.getText().toString());
        address.setAddress(editAddress.getText().toString());
        address.setTag(editTip.getText().toString());
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDefaults(switchButton.isChecked() ? "1" : "0");

        VerifyResult result = AddressVerify.verify(address);
        if (!result.getResult()) {
            showTip(result.getErrorMsg());
            return;
        }

        switch (type) {

            case TYPE_ADD:
                new AddAddressAPI(this, address);
                break;
            case TYPE_UPDATE:
                new UpdateAddressAPI(this, address);
                break;
        }
    }

    @Override
    public void apiAddAddressSuccess() {
        showTip("修改成功");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void apiAddAddressFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiUpdateAddressSuccess() {
        showTip("修改成功");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void apiUpdateAddressFailure(long code, String msg) {
        showTip(msg);
    }

    /**
     * 获取定位位置
     */
    @OnClick(R.id.act_address_edit_locate)
    public void getLocation(){
        new LocationAPI(getApplicationContext(),this);
    }

    @Override
    public void apiLocateSuccess(AMapLocation location) {
        textDistrct.setText(location.getProvince() + " " + location.getCity() + " " + location.getDistrict());
        editAddress.setText(location.getRoad());
    }

    @Override
    public void apiLocateFailure(AMapLocation location) {
        TipUtils.toast("定位失败" + location.getErrorCode());
    }
}

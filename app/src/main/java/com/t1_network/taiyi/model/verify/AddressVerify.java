package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Address;

/**
 * Created by David on 15/12/8.
 */
public class AddressVerify extends BasicVerify {

    public static VerifyResult verify(Address address) {

        AddressVerify verify = new AddressVerify();

        if (TextUtils.isEmpty(address.getName())) {
            return verify.error(R.string.error_address_name_is_empty);
        }

        VerifyResult mobileVerify = MobileVerify.verifyMobile(address.getPhone());

        if (!mobileVerify.getResult()) {
            return verify.error(mobileVerify.getErrorMsg());
        }

        if (TextUtils.isEmpty(address.getProvince()) || TextUtils.isEmpty(address.getCity()) || TextUtils.isEmpty(address.getDistrict())) {
            return verify.error(R.string.error_address_district_is_empty);
        }

        if (TextUtils.isEmpty(address.getAddress())) {
            return verify.error(R.string.error_address_address_is_empty);
        }

        return verify.success();
    }


}

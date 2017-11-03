package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

import com.t1_network.taiyi.R;

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
public class UserVerify extends BasicVerify {

    public static VerifyResult verify(String name){
        UserVerify userVerify = new UserVerify();
        if (TextUtils.isEmpty(name))
            return userVerify.error(R.string.error_name_code_can_no_be_enpty);
        return userVerify.success();
    }

}

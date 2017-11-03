package com.t1_network.taiyi.model.verify;

import android.text.TextUtils;

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
public class InvoiceTitleVerify extends BasicVerify {

    public static VerifyResult verify(String question){
        InvoiceTitleVerify questionVerify = new InvoiceTitleVerify();
        if (TextUtils.isEmpty(question))
            return questionVerify.error("请填写发票抬头信息");

        return questionVerify.success();
    }

}

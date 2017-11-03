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
public class InvoiceRemarksVerify extends BasicVerify {

    public static VerifyResult verify(String question){
        InvoiceRemarksVerify questionVerify = new InvoiceRemarksVerify();
        if (TextUtils.isEmpty(question))
            return questionVerify.error("请输入备注");
        return questionVerify.success();
    }

}

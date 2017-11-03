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
public class QuestionVerify extends BasicVerify {

    public static VerifyResult verify(String question){
        QuestionVerify questionVerify = new QuestionVerify();
        if (TextUtils.isEmpty(question))
            return questionVerify.error("问题描述不能为空");

        return questionVerify.success();
    }

}

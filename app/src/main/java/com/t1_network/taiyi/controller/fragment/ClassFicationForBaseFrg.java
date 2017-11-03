package com.t1_network.taiyi.controller.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicFrg;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.home.Classfication;
import com.t1_network.taiyi.widget.ClassFictionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by David on 2015/12/8.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class ClassFicationForBaseFrg extends BasicFrg {


    @Bind(R.id.frg_class_for_base_layout_classfication)
    LinearLayout layoutClassfication;

    private List<Classfication> classficationList;

    public ClassFicationForBaseFrg() {
        super(R.layout.frg_class_for_base);
    }


    @Override
    public void initView(View view) {

        Bundle bundle = getArguments();
        classficationList = bundle.getParcelableArrayList("classfication");
        cvList.clear();
        for (int i = 0; i < classficationList.size(); i++) {

            Classfication classfication = classficationList.get(i);

            ClassFictionView cv = new ClassFictionView(context);
            cv.setTv(classfication.getName());
            cv.setData(classfication.getChildList());
            if (i == 0 ){
                cv.mGl.setVisibility(View.VISIBLE);
            }else{
                cv.mGl.setVisibility(View.GONE);
            }
            cv.setTextType();

            cvList.add(cv);

            cv.setOnClickListener(new OnClassficationItemClick(cvList));

            layoutClassfication.addView(cv);
        }
    }

    private List<ClassFictionView> cvList = new ArrayList<ClassFictionView>();

    private class OnClassficationItemClick implements View.OnClickListener {

        private List<ClassFictionView> cvList;

        public OnClassficationItemClick(List<ClassFictionView> cvList) {
            this.cvList = cvList;
        }

        @Override
        public void onClick(View v) {
            initItem(v);
        }

        private void initItem(View v) {

            for (int i = 0; i < cvList.size(); i++) {
                ClassFictionView cv = cvList.get(i);
                cv.mGl.setVisibility(View.GONE);
                cv.setTextType();
            }

            ClassFictionView selectView = (ClassFictionView) v;
            selectView.mGl.setVisibility(View.VISIBLE);
            selectView.setTextType();
        }


    }

}

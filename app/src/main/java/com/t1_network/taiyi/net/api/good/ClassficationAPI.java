package com.t1_network.taiyi.net.api.good;

import com.t1_network.taiyi.model.bean.ClassItem;
import com.t1_network.taiyi.model.bean.Classfication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 15/11/17.
 */
public class ClassficationAPI {

    private ClassficationAPIListener listener;

    public ClassficationAPI(ClassficationAPIListener listener) {
        this.listener = listener;


        List<ClassItem> classItemList = new ArrayList<ClassItem>();
        ClassItem i1 = null;

        i1 = new ClassItem();
        i1.setId(1);
        i1.setName("血压计");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/1xueyayi.png");
        classItemList.add(i1);

        i1 = new ClassItem();
        i1.setId(2);
        i1.setName("体重计");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/2xuetangji.png");
        classItemList.add(i1);

        i1 = new ClassItem();
        i1.setId(2);
        i1.setName("体重计");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/2xuetangji.png");
        classItemList.add(i1);

        i1 = new ClassItem();
        i1.setId(2);
        i1.setName("体重计");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/2xuetangji.png");
        classItemList.add(i1);

        i1 = new ClassItem();
        i1.setId(2);
        i1.setName("血压计");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/3tiwenji.png");
        classItemList.add(i1);

        List<Classfication> classficationList = new ArrayList<Classfication>();

        Classfication o1 = new Classfication();
        o1.setIcon("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/icon/huli%402x.png");
        o1.setName("日用家常");
        o1.setClassItemList(classItemList);
        classficationList.add(o1);

        Classfication o2 = new Classfication();
        o2.setName("呼吸相关");
        o2.setClassItemList(classItemList);
        classficationList.add(o2);


        List<String> diseaseList = new ArrayList<String>();
        diseaseList.add("心脏病");
        diseaseList.add("糖尿病");
        diseaseList.add("高血压");
        diseaseList.add("脑血栓");
        diseaseList.add("颈椎病");
        diseaseList.add("肩周炎");
        diseaseList.add("冠心病");
        diseaseList.add("肺心病");
        diseaseList.add("耳聋");


        List<ClassItem> peopleList = new ArrayList<ClassItem>();

        i1 = new ClassItem();
        i1.setId(1);
        i1.setName("老人");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/icon/28laoren.png");
        peopleList.add(i1);
        i1 = new ClassItem();
        i1.setId(1);
        i1.setName("母婴");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/icon/29muying.png");
        peopleList.add(i1);
        i1 = new ClassItem();
        i1.setId(1);
        i1.setName("儿童");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/icon/30ertong.png");
        peopleList.add(i1);
        i1 = new ClassItem();
        i1.setId(1);
        i1.setName("中青年");
        i1.setImage("http://taiyitest.oss-cn-hangzhou.aliyuncs.com/icon/31zhongqingnian.png");
        peopleList.add(i1);

        listener.apiClassficationSuccess(classficationList, diseaseList, peopleList);
    }

    public interface ClassficationAPIListener {
        public void apiClassficationSuccess(List<Classfication> classficationList, List<String> diseaseList, List<ClassItem> peopleList);

        public void apiClassficationFailure(long code, String msg);
    }

}

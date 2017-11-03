package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.widget.ImageLooker.ImageLookerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TestAct extends BasicAct {

    public TestAct() {
        super(R.layout.activity_test, R.string.title_activity_test);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestAct.class);
        context.startActivity(intent);
    }

    private static final String P_IMAGE_LIST = "P_IMAGE_LIST";

    public static void startActivity(Context context, List<Bitmap> imageList) {
        Intent intent = new Intent(context, TestAct.class);
        intent.putParcelableArrayListExtra(P_IMAGE_LIST, (ArrayList) imageList);
        context.startActivity(intent);
    }


    @Bind(R.id.imageViewTouch)
    ImageLookerView imageLookerView;

    @Override
    public void initView() {

        Intent intent = getIntent();

        ArrayList<Bitmap> imageViewList = intent.getParcelableArrayListExtra(P_IMAGE_LIST);



    }


}

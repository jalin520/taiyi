package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.OrderWaitComment;
import com.t1_network.taiyi.model.event.UpdateOrderEvent;
import com.t1_network.taiyi.model.event.UpdateUserEvent;
import com.t1_network.taiyi.model.verify.EvaVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.common.UploadImageAPI;
import com.t1_network.taiyi.net.api.order.OrderEvaAPI;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class EvaluateAct extends BasicAct implements AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImageCropCompleteListener,
        AndroidImagePicker.OnImagePickCompleteListener, UploadImageAPI.UploadImageAPIListener, OrderEvaAPI.OrderEvaAPIListener {

    @Bind(R.id.act_evaluate_ll)
    LinearLayout mLayoutImage;

    @Bind(R.id.act_evaluate_et)
    EditText editText;

    @Bind(R.id.act_evaluate_rating)
    RatingBar ratingBar;

    @Bind(R.id.act_evaluate_iv_icon)
    ImageView imageView;

    @Bind(R.id.act_evaluate_text_good_name)
    TextView textGoodName;


    @Bind({R.id.image_evaluate_1, R.id.image_evaluate_2, R.id.image_evaluate_3, R.id.image_evaluate_4, R.id.image_evaluate_5, R.id.image_evaluate_6, R.id.image_evaluate_7, R.id.image_evaluate_8})
    List<ImageView> imageViewList;


    public EvaluateAct() {
        super(R.layout.act_evaluate, R.string.title_activity_evaluate);
    }

    public static final String P_ORDER = "P_ORDER";

    private OrderWaitComment order;

    public static void startActivity(Context context, OrderWaitComment order) {
        Intent intent = new Intent(context, EvaluateAct.class);
        intent.putExtra(P_ORDER, order);
        context.startActivity(intent);
    }

    @Override
    public void initView() {


        Intent intent = getIntent();
        order = intent.getParcelableExtra(P_ORDER);

        imageLoader.displayImage(order.getImage(), imageView);
        textGoodName.setText(order.getGoodName());

        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);//watching Picture taking
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);
    }

    public View addImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_evaluate_iv, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_evaluate_fl_iv);


        imageView.setImageResource(R.drawable.test_evaluate);

        AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
        alpha.setDuration(2000);
        imageView.setAnimation(alpha);
        return view;
    }


    @OnClick({R.id.image_evaluate_1, R.id.image_evaluate_2, R.id.image_evaluate_3, R.id.image_evaluate_4, R.id.image_evaluate_5, R.id.image_evaluate_6, R.id.image_evaluate_7, R.id.image_evaluate_8})
    public void onClickAddImage(View view) {


        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);

        Intent intent = new Intent();
        int requestCode = 1433;
        intent.setClass(this, ImagesGridActivity.class);
        startActivityForResult(intent, requestCode);

    }


    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);

        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            LogUtils.e("RESULT_OK");
            if (requestCode == 1433) {
                LogUtils.e("1433");
                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();

                for (int i = 0; i < imageList.size(); i++) {
                    LogUtils.e(imageList.get(i).name + " " + imageList.get(i).path);
                }
            }
        }
    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        LogUtils.e("onImageCropComplete");
    }


    private int imageCount = 0;

    private List<String> imagePathList = new ArrayList<String>();

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        LogUtils.e("onImagePickComplete");
        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();

        for (int i = 0; i < imageList.size(); i++) {

            if (i + imageCount >= 8) {
                break;
            }

            imageLoader.displayImage("file://" + imageList.get(i).path, imageViewList.get(i + imageCount));
            imageViewList.get(i + imageCount).setVisibility(View.VISIBLE);
            imagePathList.add(imageList.get(i).path);

        }

        imageCount += imageList.size();

        if (imageCount < 8) {
            imageViewList.get(imageCount).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        LogUtils.e("onPictureTakeComplete");
//        LogUtils.e(picturePath);
    }

    @OnClick(R.id.act_evaluate_root)
    public void onClickRoot(View v){
        KeyBoardUtils.hideKeyboard(this,v);
    }

    //提交按钮
    @OnClick(R.id.act_evaluate_btn_commit)
    public void onClickCommit(View view) {

        float rating = ratingBar.getRating();

        int rate = (int) rating;

        String content = editText.getText().toString();

        VerifyResult result = EvaVerify.verify(rating, content);

        if (!result.getResult()) {
            showTip(result.getErrorMsg());
            return;
        }

        if (imageCount == 0) {
            new OrderEvaAPI(this, order.getId(), rate * 2 + "", content, null);
        } else {
            new UploadImageAPI(this, imageViewList.subList(0, imageCount));
        }
    }

    @Override
    public void apiUploadImageFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiUploadImageSuccess(JSONArray pic) {
        float rating = ratingBar.getRating();

        int rate = (int) rating;

        String content = editText.getText().toString();

        VerifyResult result = EvaVerify.verify(rating, content);

        if (!result.getResult()) {
            showTip(result.getErrorMsg());
            return;
        }
        dialog.show();

        new OrderEvaAPI(this, order.getId(), rate * 2 + "", content, pic);
    }


    @Override
    public void apiOrderEvaFailure(long code, String msg) {
        showTip(msg);
        dialog.dismiss();
    }

    @Override
    public void apiOrderEvaSuccess() {

        showTip("评论成功");
        dialog.dismiss();

        EventBus.getDefault().post(new UpdateUserEvent()); //更新用户信息
        EventBus.getDefault().post(new UpdateOrderEvent()); //更新订单

        finish();
    }
}

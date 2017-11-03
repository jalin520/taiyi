package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.Consumer;
import com.t1_network.taiyi.model.bean.order.OrderBackRefund;
import com.t1_network.taiyi.model.bean.order.OrderDetailBean;
import com.t1_network.taiyi.model.bean.order.ReturnOrderBean;
import com.t1_network.taiyi.model.verify.QuestionVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.common.UploadImageAPI;
import com.t1_network.taiyi.net.api.order.OrderBackRefundAPI;
import com.t1_network.taiyi.net.api.order.OrderReturnAPI;
import com.t1_network.taiyi.utils.VerifyUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ReturnSaleAct extends BasicAct implements View.OnClickListener, AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener, AndroidImagePicker.OnImageCropCompleteListener, UploadImageAPI.UploadImageAPIListener, OrderReturnAPI.OnOrderReturnAPIListener, OrderBackRefundAPI.OrderBackRefundAPIListener {

    private static final String P_ORDER = "ORDER";
    private static final String P_ADDRESS = "ADDRESS";
    private OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail;
    private ReturnOrderBean mBean;
    private OrderBackRefund refundBean;
    private int i;
    private String mAddress;

    public ReturnSaleAct() {
        super(R.layout.act_return_sale, R.string.title_activity_return_sale);
    }

    @Bind(R.id.act_return_content_ll)
    LinearLayout llContent;
    //    退货refund
    @Bind(R.id.return_sale_text_amount_refund)
    TextView tvRefund;
    @Bind(R.id.return_sale_btn_service_type_1)
    Button btnTypeReturn;//退货
    @Bind(R.id.return_sale_btn_service_type_2)
    Button btnTypeChange;//换货
    //处理数量
    @Bind(R.id.c_num_picker_image_del)
    ImageView ivDel;
    @Bind(R.id.c_num_picker_image_add)
    ImageView ivAdd;
    @Bind(R.id.c_num_picker_text_num)
    TextView tvNum;
    @Bind(R.id.return_sale_text_amout_tip)
    TextView tvTip;
    //问题描述.输入框
    @Bind(R.id.return_sale_edittext_question)
    EditText etQuestion;
    //上传图片
    @Bind({R.id.image_evaluate_1, R.id.image_evaluate_2, R.id.image_evaluate_3, R.id.image_evaluate_4})
    List<ImageView> imageViewList;


    public static void startActivity(Context context, OrderDetailBean.OrderEntity.OrderdetailEntity orderdetail, String address) {
        Intent intent = new Intent(context, ReturnSaleAct.class);
        intent.putExtra(P_ORDER, orderdetail);
        intent.putExtra(P_ADDRESS, address);
        context.startActivity(intent);

    }

    @Override
    public void initView() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = this.getIntent();
        orderdetail = intent.getParcelableExtra(P_ORDER);
        mAddress = intent.getStringExtra(P_ADDRESS);

        //初始化数据
        initData();
        //初始化商品显示
        initContentShow();
        //选择退换货的type
        initContentType();
        //处理退换货的数量
        initContentCount();
        //处理退换货的上传图片
        initContentUploadImage();


    }


    private void initData() {
        //初始化退换货申请的基本数据
        if (mBean == null)
            mBean = new ReturnOrderBean();
        Consumer consumer = App.getApp().getUser().getConsumer();
        mBean.setOrderid(orderdetail.getOrderid());
        mBean.setProductid(orderdetail.getProductid());
        mBean.setUserid(consumer.getId());
        mBean.setName(consumer.getUsername());
        mBean.setPhone(consumer.getPhone());
        mBean.setAddress(mAddress);
        mBean.setFreight("10");
        mBean.setRefund("0.1");
        mBean.setType("1");
        mBean.setQuantity("1");
        tvNum.setText("1");
    }


    //初始化头部显示
    private void initContentShow() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_order_wait_pay_item, null);
        view.findViewById(R.id.item_order_image).setVisibility(View.INVISIBLE);
        if (VerifyUtils.hasImage(orderdetail)) {
            ImageView ivIcon = (ImageView) view.findViewById(R.id.good_image);
            imageLoader.displayImage(orderdetail.getProductpic().get(0).getUrl(), ivIcon);
        }
        TextView tvProductname = (TextView) view.findViewById(R.id.orderName);
        TextView tvPrice = (TextView) view.findViewById(R.id.order_is_seven);
        TextView tvCount = (TextView) view.findViewById(R.id.orderAmount);
        tvProductname.setText(orderdetail.getProductname());
        tvPrice.setText(ResUtils.getString(R.string.money, orderdetail.getPrice()));
        tvCount.setText(ResUtils.getString(R.string.count, orderdetail.getQuantity()));
        llContent.addView(view);

    }

    //初始化退货的type
    private void initContentType() {
        btnTypeReturn.setOnClickListener(this);
        btnTypeChange.setOnClickListener(this);
    }

    //初始化退换货的商品数量
    private void initContentCount() {
        tvTip.setText(ResUtils.getString(R.string.return_sale_text_amout_tip, orderdetail.getQuantity()));
        new OrderBackRefundAPI(this, mBean.getOrderid(), mBean.getProductid(), 1 + "");
        i = Integer.parseInt(mBean.getQuantity());
        ivDel.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    //退换货问题描述
    private boolean initContentQuestion() {
        String question = etQuestion.getText().toString().trim();
        VerifyResult verifyResult = QuestionVerify.verify(question);
        if (!verifyResult.getResult()) {
            showTip(verifyResult.getErrorMsg());
            return false;
        }
        mBean.setReason(question);
        return true;
    }

    //提交按钮
    @OnClick(R.id.act_return_sale_btn_submit)
    public void submit() {

        if (!initContentQuestion()) {
            return;
        }

        if (imageCount != 0) {
            new UploadImageAPI(this, imageViewList.subList(0, imageCount));
        }
        new OrderReturnAPI(this, mBean, refundBean.getRefund() + "");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.return_sale_btn_service_type_1://退货
                mBean.setType("1");
                btnTypeReturn.setBackgroundResource(R.drawable.ic_return_sale_selected);
                btnTypeReturn.setTextColor(ResUtils.getColor(R.color.text_orange));
                btnTypeChange.setBackgroundResource(R.drawable.bg_btn_after_sale_2);
                btnTypeChange.setTextColor(ResUtils.getColor(R.color.text_gray_mid));
                break;
            case R.id.return_sale_btn_service_type_2://换货
                mBean.setType("2");
                btnTypeReturn.setBackgroundResource(R.drawable.bg_btn_after_sale_2);
                btnTypeReturn.setTextColor(ResUtils.getColor(R.color.text_gray_mid));
                btnTypeChange.setBackgroundResource(R.drawable.ic_return_sale_selected);
                btnTypeChange.setTextColor(ResUtils.getColor(R.color.text_orange));
                break;
            case R.id.c_num_picker_image_del://减
                if (i > 1) {
                    i--;
                    tvNum.setText(i + "");
                    mBean.setQuantity(i + "");
                    new OrderBackRefundAPI(this, mBean.getOrderid(), mBean.getProductid(), mBean.getQuantity());
                }

                break;
            case R.id.c_num_picker_image_add://加
                String quantity = orderdetail.getQuantity();
                int count = Integer.parseInt(quantity);
                if (i < count) {
                    i++;
                    tvNum.setText(i + "");
                    mBean.setQuantity(i + "");
                    new OrderBackRefundAPI(this, mBean.getOrderid(), mBean.getProductid(), mBean.getQuantity());
                }
                break;

        }
    }

    private void initContentUploadImage() {
        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);

    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        LogUtils.e("onPictureTakeComplete");
    }

    private int imageCount = 0;

    private List<String> imagePathList = new ArrayList<String>();

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        LogUtils.e("onImagePickComplete");
        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();

        for (int i = 0; i < imageList.size(); i++) {

            if (i + imageCount >= 4) {
                break;
            }

            imageLoader.displayImage("file://" + imageList.get(i).path, imageViewList.get(i + imageCount));
            imageViewList.get(i + imageCount).setVisibility(View.VISIBLE);
            imagePathList.add(imageList.get(i).path);

        }

        imageCount += imageList.size();

        if (imageCount < 4) {
            imageViewList.get(imageCount).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        LogUtils.e("onImageCropComplete");
    }

    @OnClick({R.id.image_evaluate_1, R.id.image_evaluate_2, R.id.image_evaluate_3, R.id.image_evaluate_4})
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
    public void apiUploadImageSuccess(JSONArray pic) {
        mBean.setProof(pic);
    }

    @Override
    public void apiUploadImageFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void onOrderReturnAPISuccess() {
        showTip("提交成功...正在为您处理");
        finish();
    }

    @Override
    public void onOrderReturnAPIFailure(long code, String msg) {
        showTip(msg);
    }

    @OnClick(R.id.return_sale_relativelayout_question)
    public void onClickRoot(View v) {
        KeyBoardUtils.hideKeyboard(this, v);
    }

    @Override
    public void apiOrderBackRefundSuccess(OrderBackRefund bean) {
        this.refundBean = bean;
        tvRefund.setText(ResUtils.getString(R.string.return_sale_text_amount_refund,bean.getRefund(),bean.getAmount(),bean.getVoucher()));
    }

    @Override
    public void apiOrderBackRefundFailure(long code, String msg) {
        showTip(msg);
    }
}

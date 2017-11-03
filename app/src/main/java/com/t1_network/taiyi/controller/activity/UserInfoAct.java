package com.t1_network.taiyi.controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.t1_network.core.app.App;
import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.User;
import com.t1_network.taiyi.model.event.UserInfoEvent;
import com.t1_network.taiyi.model.verify.UserNameVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;
import com.t1_network.taiyi.net.api.common.UploadImageAPI;
import com.t1_network.taiyi.net.api.user.UserInfoAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class UserInfoAct extends BasicAct implements AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener, AndroidImagePicker.OnImageCropCompleteListener, UploadImageAPI.UploadImageAPIListener, UserInfoAPI.UserInfoAPIListener {

    @Bind(R.id.act_setting_user_info_nickname)
    public EditText etNickName;

    @Bind(R.id.act_setting_user_info_tv_sex)
    public TextView tvSex;

    @Bind(R.id.act_setting_user_info_tv_age)
    public TextView tvAge;

    @Bind(R.id.frg_user_info_text_login_or_register_iv_user_icon)
    List<ImageView> imageViewList;


    private String userSex;
    private String[] sexArr = new String[]{"男", "女"};
    private String date;
    private String mUserName;
    private String mUrl;

    private User mUser;


    public UserInfoAct() {
        super(R.layout.activity_user_info, R.string.title_activity_already_user_info, true);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfoAct.class);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        mUser =App.getApp().getUser();
        etNickName.setText(mUser.getConsumer().getNickname());

        tvAge.setText(mUser.getConsumer().getBirthday());

        tvSex.setText(mUser.getConsumer().getSex().equals("1") ? "男" : "女");

        imageLoader.displayImage(mUser.getConsumer().getPhoto(), imageViewList.get(0));

        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);//watching Picture taking
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);
    }


    //提交修改资料
    @OnClick(R.id.compar_user_info_comfirm)
    public void onClickComfirm(View v) {
        mUser =App.getApp().getUser();
        mUserName = etNickName.getText().toString().trim();
        VerifyResult verifyResult = UserNameVerify.verify(mUserName);
        if (!verifyResult.getResult()) {
            showTip(verifyResult.getErrorMsg());
            return;
        }
        mUser.getConsumer().setNickname(mUserName);
        if (imageCount == 0) {
            new UserInfoAPI(this
                    , mUser.getConsumer().getNickname()
                    , mUser.getConsumer().getSex()
                    , mUser.getConsumer().getBirthday()
                    , mUser.getConsumer().getPhoto());
        } else {
            new UploadImageAPI(this, imageViewList.subList(0, imageCount));
        }


    }

    //点击更换头像
    @OnClick(R.id.act_setting_user_info_icon)
    public void onClickUserinfoIcon(View v) {
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);

        Intent intent = new Intent();
        int requestCode = 1433;
        intent.setClass(this, ImagesGridActivity.class);
        startActivityForResult(intent, requestCode);
    }

    //点击性别
    @OnClick(R.id.act_setting_user_info_sex)
    public void onClickUserinfoSex(View v) {

        new AlertDialog.Builder(this)
                .setTitle("请选择")
                .setItems(sexArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userSex = sexArr[which];
                        tvSex.setText(userSex);
                        String sex =  userSex.equals("男") ? "1" : "0";
                        mUser.getConsumer().setSex(sex);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //点击了出生日期
    @OnClick(R.id.act_setting_user_info_age)
    public void onClickUserinfoAge(View v) {
        DatePickerDialog dataDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = year + "" + (monthOfYear + 1) + "" + dayOfMonth;
                tvAge.setText(date);
                mUser.getConsumer().setBirthday(date);
            }
        }, 2016, 1, 1);
        dataDialog.show();
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        LogUtils.e("onImageCropComplete");
    }


    private int imageCount = 0;

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
        for (int i = 0; i < imageList.size(); i++) {
            imageLoader.displayImage("file://" + imageList.get(i).path, imageViewList.get(0));
        }
        imageCount = 1;
    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        LogUtils.e("onImageCropComplete");
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
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        super.onDestroy();
    }

    @Override
    public void apiUploadImageSuccess(JSONArray pic) {
        LogUtils.e(pic.toString());
        try {
            JSONObject jsonObject = pic.getJSONObject(0);
            mUrl = jsonObject.getString("url");
            mUser.getConsumer().setPhoto(mUrl);
            new UserInfoAPI(this, mUser.getConsumer().getNickname()
                    , mUser.getConsumer().getSex()
                    , mUser.getConsumer().getBirthday()
                    , mUser.getConsumer().getPhoto());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void apiUploadImageFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiUserInfoSuccess() {

        EventBus.getDefault().post(new UserInfoEvent(mUser));
        finish();
    }

    @Override
    public void apiUserInfoFailure(long code, String msg) {
        showTip(msg);
    }
}

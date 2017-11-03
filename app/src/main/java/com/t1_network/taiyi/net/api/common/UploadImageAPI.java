package com.t1_network.taiyi.net.api.common;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.t1_network.core.app.Build;
import com.t1_network.core.utils.JsonUtils;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class UploadImageAPI extends TYAPI {

    private UploadImageAPIListener listener;


    private String BOUNDARY = "--------------taiyi-yijianyouxuan"; //数据分隔线
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    public UploadImageAPI(UploadImageAPIListener listener, List<ImageView> imageViewList) {
        this.listener = listener;
        addUserAuthorization();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        for (int i = 0; i < imageViewList.size(); i++) {

            imageViewList.get(i).setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(imageViewList.get(i).getDrawingCache());
            imageViewList.get(i).setDrawingCacheEnabled(false);

            StringBuffer sb = new StringBuffer();
            //第一行 `"--" + BOUNDARY + "\r\n"`
            sb.append("--" + BOUNDARY);
            sb.append("\r\n");

            //第二行 Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"

            sb.append("Content-Disposition: form-data;");
            sb.append(" name=\"");
            sb.append("taiyi_image");
            sb.append("\"");
            sb.append("; filename=\"");
            sb.append("taiyi" + i + ".png");
            sb.append("\"");
            sb.append("\r\n");

            //第三行 Content-Type: 文件的 mime 类型 + "\r\n"
            sb.append("Content-Type: ");
            sb.append("image/png");
            sb.append("\r\n");
            //第四行 \r\n
            sb.append("\r\n");
            //数据

            try {
                bos.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
                //文件的二进制数据 + "\r\n"
                bos.write(getValue(bitmap));
                bos.write("\r\n".getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /*结尾行*/
        //`"--" + BOUNDARY + "--" + "\r\n"`
        String endLine = "--" + BOUNDARY + "--" + "\r\n";

        try {
            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bodyByte = bos.toByteArray();
        bodyContentType = MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
        LogUtils.e(body);
        new TYHttp(this).request();
    }


    private byte[] getValue(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        return bos.toByteArray();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        LogUtils.e("apiRequestError");
        listener.apiUploadImageFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        LogUtils.e("apiRequestSuccess");
        try {
            JSONObject json = new JSONObject(data);

            JSONArray array = JsonUtils.getJSONArray(json, "result");


            listener.apiUploadImageSuccess(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getURL() {
        return Build.HOST + "upload/shop";
    }

    public interface UploadImageAPIListener {
        public void apiUploadImageSuccess(JSONArray pic);

        public void apiUploadImageFailure(long code, String msg);
    }

}

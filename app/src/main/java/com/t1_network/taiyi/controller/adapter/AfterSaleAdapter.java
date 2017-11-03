package com.t1_network.taiyi.controller.adapter;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.StringUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.AfterSale;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.List;


/**
 * Created by chenyu on 2015/11/18.
 * 售后服务
 */
public class AfterSaleAdapter extends BasicListAdapter {


    /**
     * 定义4种类型的item
     * 0-审核不通过 1-已完成 2-待审核 3-审核通过 4-已撤销 5-待校验 6-校验不通过
     */
    private final static int SALE_RETURN_FAILURE = 0;       //审批不通过
    private final static int SALE_RETURN_SUCCESS = 1;       //退货成功
    private final static int CHECK_PENDING = 2;     //待审核
    private final static int CHECK_PASS = 3;       //审核通过
    private final static int CHECK_RESCINDED = 4;       //已撤销

    private final static int VERIFICATION_PENDING = 5;       //商品寄回，待核验
    private final static int VERIFICATION_FAILURE = 6;       //校验不通过

    private Dialog mDialog;
    private AfterSaleListener mListener;

    public AfterSaleAdapter(List<?> data, AfterSaleListener mListener) {
        super(data, R.layout.item_after_sale_check_pending);
        this.mListener = mListener;

    }


    @Override
    public void bind(ViewHolder holder, int position) {
        AfterSale afterSale = (AfterSale) data.get(position);
        String statusType;
        CharSequence highLightText;
        holder.getTextView(R.id.after_sale_id).setText(ResUtils.getString(R.string.title_activity_after_sale_id, afterSale.getAfter_sale_orderid()));
        if (VerifyUtils.hasImage(afterSale)) {
            ImageView ivIcon = holder.getImageView(R.id.good_image);
            imageLoader.displayImage(afterSale.getProductpic().get(0).getUrl(), ivIcon);
        }
        holder.getTextView(R.id.orderName).setText(afterSale.getAfter_sale_productname());
        holder.getTextView(R.id.orderAmount).setText(ResUtils.getString(R.string.title_activity_after_sale_quantity, afterSale.getAfter_sale_quantity()));
        holder.getTextView(R.id.act_after_sale_item_audit_time).setText(ResUtils.getString(R.string.title_activity_after_sale_audit_time, afterSale.getAfter_sale_audittime()));

        LinearLayout sale_ll_cancle = holder.get(R.id.item_after_sale_check_pending_cancle);
        LinearLayout check_pass_query = holder.get(R.id.check_pending_query);
        LinearLayout check_deliver = holder.get(R.id.after_sale_edit_imformation);
        LinearLayout llAddress = holder.get(R.id.item_after_sale_check_pending_address);
        LinearLayout llPassCancle = holder.get(R.id.item_after_sale_check_pass_cancle);
        LinearLayout llInput = (LinearLayout) holder.get(R.id.item_after_sale_input_ptn);
        if (CHECK_PASS == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 审核通过";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_green), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            sale_ll_cancle.setVisibility(View.GONE);
            llInput.setVisibility(View.VISIBLE);
        } else if (SALE_RETURN_FAILURE == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 审核不通过";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_red), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            sale_ll_cancle.setVisibility(View.GONE);
            llInput.setVisibility(View.GONE);
        } else if (VERIFICATION_FAILURE == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 校验不通过";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_red), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            sale_ll_cancle.setVisibility(View.GONE);
            llInput.setVisibility(View.GONE);
        } else if (SALE_RETURN_SUCCESS == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 退货成功";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_orange), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            sale_ll_cancle.setVisibility(View.GONE);
            llInput.setVisibility(View.GONE);
        } else if (CHECK_PENDING == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 待审核";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_orange), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            llInput.setVisibility(View.GONE);
            sale_ll_cancle.setVisibility(View.VISIBLE);

        } else if (VERIFICATION_PENDING == Integer.parseInt(afterSale.getAfter_sale_status())) {
            statusType = "状态 : 商品寄回，待核验";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_orange), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            llInput.setVisibility(View.GONE);
            sale_ll_cancle.setVisibility(View.GONE);

        } else {
            statusType = "状态 : 已撤销";
            highLightText = StringUtils.getHighLightText(statusType, ResUtils.getColor(R.color.text_green), 4, statusType.length());
            holder.getTextView(R.id.act_after_sale_item_status).setText(highLightText);
            llInput.setVisibility(View.GONE);
            sale_ll_cancle.setVisibility(View.GONE);
        }

        //取消申请
        sale_ll_cancle.setOnClickListener(new AfterCancleListener(mListener, afterSale));
        //查看进度
        check_pass_query.setOnClickListener(new AfterSaleDetailListener(mListener, afterSale));
        //填写发货信息
        check_deliver.setOnClickListener(new afterClickDeliverClickListener(mListener, afterSale));
        //查看邮寄地址
        llAddress.setOnClickListener(new AfterCheckAddress(mListener, afterSale));
        //取消申请
        llPassCancle.setOnClickListener(new AfterCancleListener(mListener, afterSale));


    }


    public interface AfterSaleListener {

        void afterSaleDetailClick(View view, AfterSale afterSale);

        void afterCancleClick(View view, AfterSale afterSale);

        void afterCheckAddressClick(View view, AfterSale afterSale);

        void afterClickDeliverClick(View view, AfterSale afterSale);

    }

    //填写收货信息Listener
    class afterClickDeliverClickListener implements View.OnClickListener {

        private AfterSaleListener mListener;
        private AfterSale afterSale;

        public afterClickDeliverClickListener(AfterSaleListener mListener, AfterSale afterSale) {
            this.mListener = mListener;
            this.afterSale = afterSale;
        }

        @Override
        public void onClick(View v) {
            mListener.afterClickDeliverClick(v, afterSale);
        }
    }


    //查看进度的listener
    class AfterSaleDetailListener implements View.OnClickListener {

        private AfterSale afterSale;
        private AfterSaleListener mListener;

        public AfterSaleDetailListener(AfterSaleListener mListener, AfterSale afterSale) {
            this.afterSale = afterSale;
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.afterSaleDetailClick(v, afterSale);
        }
    }

    //取消申请的listener
    class AfterCancleListener implements View.OnClickListener {
        private AfterSale afterSale;
        private AfterSaleListener mListener;

        public AfterCancleListener(AfterSaleListener mListener, AfterSale afterSale) {
            this.afterSale = afterSale;
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.afterCancleClick(v, afterSale);

        }
    }

    //查看邮递的listener
    class AfterCheckAddress implements View.OnClickListener {

        private AfterSale afterSale;
        private AfterSaleListener mListener;

        public AfterCheckAddress(AfterSaleListener mListener, AfterSale afterSale) {
            this.mListener = mListener;
            this.afterSale = afterSale;
        }

        @Override
        public void onClick(View v) {
            mListener.afterCheckAddressClick(v, afterSale);
        }
    }


}

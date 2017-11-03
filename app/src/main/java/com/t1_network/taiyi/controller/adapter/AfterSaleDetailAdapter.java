package com.t1_network.taiyi.controller.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.AfterSaleProcess;

import java.util.List;

/**
 * Created by chenyu on 2015/11/19.
 */
public class AfterSaleDetailAdapter extends BasicAdapter {

    public AfterSaleDetailAdapter(List<?> data){
        super(data, R.layout.item_after_sale_process);
    }

    public void bind(ViewHolder holder, int position) {

        AfterSaleProcess afterSaleProcess = (AfterSaleProcess)data.get(position);
        holder.getTextView(R.id.after_sale_process_state).setText(afterSaleProcess.getAfter_sale_process_state());
        holder.getTextView(R.id.after_sale_process_time).setText(afterSaleProcess.getAfter_sale_process_time());

        RelativeLayout relativeLayout = holder.get(R.id.after_sale_process_views);
        ImageView circle_view = new ImageView(context);
        ImageView line_view = new ImageView(context);

        //列表为空
        if(data.size()<1){
            return;
        }
        //当列表只有一项时
        else if(data.size()==1){
            RelativeLayout.LayoutParams first_circle_lp = new RelativeLayout.LayoutParams(
                    UIUtils.dp2Px(12), UIUtils.dp2Px(12));
            first_circle_lp.setMargins(UIUtils.dp2Px(6), UIUtils.dp2Px(20), 0, 0);
            first_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            circle_view.setBackgroundResource(R.drawable.ic_check_pay_select);
            circle_view.setLayoutParams(first_circle_lp);
            circle_view.setId(R.id.after_sale_process_first_circle_id);
            relativeLayout.addView(circle_view);
        }
        //列表不止一项
        else{
            //列表的第一个item
            if(position==0){
                RelativeLayout.LayoutParams first_circle_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(12), UIUtils.dp2Px(12));
                first_circle_lp.setMargins(UIUtils.dp2Px(6), UIUtils.dp2Px(18), 0, 0);
                first_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                circle_view.setBackgroundResource(R.drawable.ic_check_pay_select);
                circle_view.setLayoutParams(first_circle_lp);
                circle_view.setId(R.id.after_sale_process_first_circle_id);

                RelativeLayout.LayoutParams first_line_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(2), UIUtils.dp2Px(60));
                first_line_lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                first_line_lp.addRule(RelativeLayout.BELOW, R.id.after_sale_process_first_circle_id);
                first_line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                line_view.setLayoutParams(first_line_lp);
            }
            //列表的最后一个item
            else if(position==data.size()-1){
                RelativeLayout.LayoutParams last_circle_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(5), UIUtils.dp2Px(5));
                last_circle_lp.setMargins(UIUtils.dp2Px(2), UIUtils.dp2Px(22), 0, 0);
                last_circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                circle_view.setBackgroundResource(R.drawable.order_detail_express_circle);
                circle_view.setLayoutParams(last_circle_lp);
                circle_view.setId(R.id.after_sale_process_last_circle_id);

                RelativeLayout.LayoutParams last_line_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(2), UIUtils.dp2Px(23));
                last_line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                line_view.setLayoutParams(last_line_lp);
            }
            //其他item
            else{
                RelativeLayout.LayoutParams circle_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(5), UIUtils.dp2Px(5));
                circle_lp.setMargins(UIUtils.dp2Px(2), UIUtils.dp2Px(22), 0, 0);
                circle_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                circle_view.setBackgroundResource(R.drawable.order_detail_express_circle);
                circle_view.setLayoutParams(circle_lp);
                circle_view.setId(R.id.after_sale_process_circle_id);

                RelativeLayout.LayoutParams line_lp = new RelativeLayout.LayoutParams(
                        UIUtils.dp2Px(2), UIUtils.dp2Px(R.dimen.after_sale_process_relativelayout_height));
                line_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                line_view.setBackgroundColor(ResUtils.getColor(R.color.bg_divide_line));
                line_view.setLayoutParams(line_lp);
            }

            relativeLayout.addView(circle_view);
            relativeLayout.addView(line_view);
        }


    }




}
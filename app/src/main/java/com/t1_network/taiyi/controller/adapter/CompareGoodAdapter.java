package com.t1_network.taiyi.controller.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.CompareGoodInfoAct;
import com.t1_network.taiyi.model.bean.good.GoodDetail;
import com.t1_network.taiyi.model.bean.good.Product;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.utils.VerifyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2015/11/24.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CompareGoodAdapter extends BasicAdapter implements View.OnClickListener {

    private ArrayList<GoodDetail> mCompareList;
    private TextView mTv;

    public CompareGoodAdapter(List<?> data, TextView mTv) {
//        super(data, R.layout.item_compar_good_select_for_delete);
        super(data, R.layout.item_compar_good_select);
        this.mTv = mTv;
        mCompareList = new ArrayList<>();
        mTv.setOnClickListener(this);
    }


    public ArrayList<GoodDetail> getCompareList() {
        return mCompareList;
    }

    @Override
    public void bind(ViewHolder holder, final int position) {

        try {
            GoodDetail goodDetail = (GoodDetail) data.get(position);
            Product product = goodDetail.getProduct();

            LinearLayout root = (LinearLayout) holder.get(R.id.item_compar_root);
            //商品名称
            holder.getTextView(R.id.item_compar_good_name).setText(product.getName());
            //商品图片

            if (VerifyUtils.hasImage(goodDetail.getProduct())){
                ImageView ivIcon = holder.getImageView(R.id.item_compar_image_good);
                imageLoader.displayImage(product.getGoodImageList().get(0).getUrl(), ivIcon, ImageOptionFactory.getGoodOptions());
            }

            //商品价格
            holder.getTextView(R.id.item_compar_good_price).setText(ResUtils.getString(R.string.money, product.getPrice()));
            //商品老价格
            TextView textView = holder.getTextView(R.id.item_compar_good_tip);
            textView.setText(ResUtils.getString(R.string.money, product.getMarketPrice()));
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            final ImageView ivCheck = holder.get(R.id.item_compar_iv_checked);
            ivCheck.setImageResource(mCompareList.contains(goodDetail) ? R.drawable.ic_checked : R.drawable.ic_check);
//        root.setOnLongClickListener(new OnClickRootListener(mItemRootListener, goodDetail,ivCheck,position));

            ivCheck.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               GoodDetail goodDetail = (GoodDetail) data.get(new Integer(position));
                                               if (!mCompareList.contains(goodDetail)) {
                                                   ivCheck.setImageResource(R.drawable.ic_checked);
                                                   mCompareList.add(goodDetail);
                                                   System.out.println(goodDetail.getProduct().getId());
                                                   if (mCompareList.size() < 2) {
                                                       //对比按钮为.normal
                                                       mTv.setBackgroundResource(R.drawable.btn_in_compar_full_normal);

                                                   } else if (mCompareList.size() == 2) {
                                                       //对比按钮为.pass
                                                       mTv.setBackgroundResource(R.drawable.btn_in_compar_full_info);


                                                   } else if (mCompareList.size() > 2) {
                                                       //不能大于2个对比
                                                       mCompareList.remove(goodDetail);
                                                       ivCheck.setImageResource(R.drawable.ic_check);
                                                   }
                                               } else {
                                                   ivCheck.setImageResource(R.drawable.ic_check);
                                                   //对比按钮为.normal
                                                   mTv.setBackgroundResource(R.drawable.btn_in_compar_full_normal);
                                                   mCompareList.remove(goodDetail);

                                               }
                                           }
                                       }

            );
        } catch (Exception e) {
            TipUtils.toast("加载失败");
        }


    }


    @Override
    public void onClick(View v) {
        if (mCompareList.size() < 2) {
            TipUtils.toast("请选择两个商品进行对比");
        } else {
            CompareGoodInfoAct.startActivity(context, mCompareList);
        }

    }

}

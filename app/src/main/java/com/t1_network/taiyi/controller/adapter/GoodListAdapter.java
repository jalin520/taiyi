package com.t1_network.taiyi.controller.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.activity.GoodDetailAct;
import com.t1_network.taiyi.model.bean.good.Good;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;
import com.t1_network.taiyi.utils.VerifyUtils;
import com.t1_network.taiyi.widget.ScoreView;

import java.util.List;

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
public class GoodListAdapter extends BasicListAdapter {

    private AddCompareListener mListener;

    public GoodListAdapter(List<?> data) {
        super(data, R.layout.item_good_list_rv_item);
    }

    public void setListener(AddCompareListener listener) {
        mListener = listener;
    }

    @Override
    public void bind(ViewHolder holder, int position) {

        Good good = (Good) data.get(position);


        RelativeLayout layoutImage = (RelativeLayout) holder.get(R.id.item_good_list_rl_icon);
        ImageView imageGood = (ImageView) holder.get(R.id.item_good_list_image_good);

        holder.getTextView(R.id.item_good_list_text_price).setText(ResUtils.getString(R.string.money, good.getPrice()));
        holder.getTextView(R.id.item_good_list_text_name).setText(good.getName());


        holder.getTextView(R.id.act_good_detail_text_operator).setText(getScore(good.getScoreOperate()) + "");
        holder.getTextView(R.id.act_good_detail_text_perference).setText(getScore(good.getScorePerformance()) + "");
        holder.getTextView(R.id.act_good_detail_text_pro_score).setText(getScore(good.getScoreBrand()) + "");


        ((ScoreView) holder.get(R.id.act_good_list_score_good)).setScore(getScore(good.getScoreOperate()));
        ((ScoreView) holder.get(R.id.act_good_list_score_opearte)).setScore(getScore(good.getScorePerformance()));
        ((ScoreView) holder.get(R.id.act_good_list_score_property)).setScore(getScore(good.getScoreBrand()));


        if (VerifyUtils.hasImage(good)) {
            imageLoader.displayImage(good.getImageList().get(0).getUrl(), imageGood, ImageOptionFactory.getGoodOptions());
        }


        layoutImage.setOnClickListener(new OnClickItemListener(good));

        //加入对比
        LinearLayout llAddCompare = (LinearLayout) holder.get(R.id.item_collect_rv_item_ll_add_compare);
        llAddCompare.setOnClickListener(new CollectionListener(good) {
            @Override
            public void collectionOnClick(Good mGood, View v) {
                mListener.AddCompare(mGood);
            }
        });


    }


    private int getScore(String score) {
        return Integer.parseInt(score == null ? "0" : score);
    }


    public interface AddCompareListener {
        void AddCompare(Good mGood);
    }


    private abstract class CollectionListener implements View.OnClickListener {
        private Good mGood;

        public CollectionListener(Good mGood) {
            this.mGood = mGood;
        }

        @Override
        public void onClick(View v) {
            collectionOnClick(mGood, v);
        }

        public abstract void collectionOnClick(Good mGood, View v);

    }


    public class OnClickItemListener implements View.OnClickListener {

        private Good good;

        public OnClickItemListener(Good good) {
            this.good = good;
        }

        @Override
        public void onClick(View v) {
            GoodDetailAct.startActivity(context, good.getId());
        }
    }


}

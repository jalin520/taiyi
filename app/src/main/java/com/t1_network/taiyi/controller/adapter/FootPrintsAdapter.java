package com.t1_network.taiyi.controller.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
 * Created by David on 2015/12/14.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class FootPrintsAdapter extends BasicListAdapter {

    private AddCompareListener mListener;

    public FootPrintsAdapter(List<?> data) {
        super(data, R.layout.item_collect_rv_item);
    }

    private OnLongClickItemListener mOnLongClickItemListener;

    public OnLongClickItemListener getOnLongClickItemListener() {
        return mOnLongClickItemListener;
    }

    public void setOnLongClickItemListener(OnLongClickItemListener onLongClickItemListener) {
        mOnLongClickItemListener = onLongClickItemListener;
    }

    public void setListener(AddCompareListener listener) {
        mListener = listener;
    }

    @Override
    public void bind(ViewHolder holder, int position) {
        Good mGood = (Good) data.get(position);

        //头部
        holder.getTextView(R.id.item_collect_rv_content_tv_title).setText(mGood.getName());


        //实价
        String price = ResUtils.getString(R.string.money, mGood.getMarketPrice());
        holder.getTextView(R.id.item_collect_rv_item_original).setText(price);

        ImageView imageView = holder.getImageView(R.id.item_collect_rv_item_icon);

        if (VerifyUtils.hasImage(mGood)) {
            imageLoader.displayImage(mGood.getImageList().get(0).getUrl(), imageView, ImageOptionFactory.getGoodOptions());
        } else {
            imageLoader.displayImage("", imageView, ImageOptionFactory.getGoodOptions());
        }

        holder.getTextView(R.id.item_collect_rv_content_text_score).setText(getScore(mGood.getScoreBrand()) + "");
        holder.getTextView(R.id.item_collect_rv_content_score_opearte_text_score).setText(getScore(mGood.getScoreOperate()) + "");
        holder.getTextView(R.id.item_collect_rv_content_score_property_text_score).setText(getScore(mGood.getScorePerformance()) + "");

        ((ScoreView) holder.get(R.id.item_collect_rv_content_score)).setScore(getScore(mGood.getScoreBrand()));
        ((ScoreView) holder.get(R.id.item_collect_rv_content_score_opearte)).setScore(getScore(mGood.getScoreOperate()));
        ((ScoreView) holder.get(R.id.item_collect_rv_content_score_property)).setScore(getScore(mGood.getScorePerformance()));

        ((LinearLayout) holder.get(R.id.item_collect_rv_item)).setOnClickListener(new OnClickGoodListener(mGood.getId()));
//        ((LinearLayout) holder.get(R.id.item_collect_rv_item)).setOnLongClickListener(new OnLongClickGoodListener(mOnLongClickItemListener, mGood.getId()));


        //加入对比
        LinearLayout llAddCompare = (LinearLayout) holder.get(R.id.item_collect_rv_item_ll_add_compare);
        if (!llAddCompare.hasOnClickListeners()) {
            llAddCompare.setOnClickListener(new CollectionListener(mGood) {
                @Override
                public void collectionOnClick(Good mGood, View v) {
                    mListener.AddCompare(mGood);
                }
            });
        }
    }


    private int getScore(String score) {
        return Integer.parseInt(score == null ? "0" : score);
    }

    public class OnClickGoodListener implements View.OnClickListener {

        private String goodId;

        public OnClickGoodListener(String goodId) {
            this.goodId = goodId;
        }

        @Override
        public void onClick(View v) {
            GoodDetailAct.startActivity(context, goodId);
        }
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

    public interface AddCompareListener {
        void AddCompare(Good mGood);
    }

    public interface OnLongClickItemListener{
        void OnLongClickItemGoodListener(String id);
    }

    public class OnLongClickGoodListener implements View.OnLongClickListener {

        private String goodId;
        private OnLongClickItemListener mListener;

        public OnLongClickGoodListener(OnLongClickItemListener listener,String goodId) {
            this.goodId = goodId;
            this.mListener= listener;
        }


        @Override
        public boolean onLongClick(View v) {
            mListener.OnLongClickItemGoodListener(goodId);
            return true;
        }
    }

}

package com.t1_network.taiyi.controller.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.t1_network.core.controller.BasicListAdapter;
import com.t1_network.core.controller.ViewHolder;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.core.utils.UIUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.good.Comment;
import com.t1_network.taiyi.model.factory.ImageOptionFactory;

import java.util.List;

/**
 * Created by David on 2015/11/26.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class GoodAppraiseAdapter extends BasicListAdapter {

    public GoodAppraiseAdapter(List<?> data) {
        super(data, R.layout.item_good_appraise);
    }

    @Override
    public void bind(ViewHolder holder, int position) {

        Comment comment = (Comment) data.get(position);
        holder.getTextView(R.id.act_good_appraise_tv).setText(comment.getContent());

        if (!TextUtils.isEmpty(comment.getReplyContent())) {
            holder.getTextView(R.id.act_good_appraise_tv_reply).setVisibility(View.VISIBLE);
            holder.getTextView(R.id.act_good_appraise_tv_reply).setText(ResUtils.getString(R.string.act_good_detail_comment_text_reply, comment.getReplyContent()));
        } else {
            holder.getTextView(R.id.act_good_appraise_tv_reply).setVisibility(View.GONE);
        }

        GridLayout gridLayout = (GridLayout) holder.get(R.id.act_good_appraise_gl);


        int width = (UIUtils.getWidthByPx() - UIUtils.dp2Px(16)) / 4;

        for (int i = 0; i < comment.getImageList().size(); i++) {

            View showGood = LayoutInflater.from(context).inflate(R.layout.item_appraise_iv, null);
            ImageView iv = (ImageView) showGood.findViewById(R.id.item_appraise_fl_iv);
            imageLoader.displayImage(comment.getImageList().get(i).getUrl(), iv, ImageOptionFactory.getGoodOptions());

            GridLayout.LayoutParams gl = new GridLayout.LayoutParams();
            gl.width = width;
            gl.height = width;

            gridLayout.addView(showGood, gl);

        }

    }
}

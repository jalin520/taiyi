package com.t1_network.taiyi.net.api.good;

import com.t1_network.core.app.Build;
import com.t1_network.taiyi.model.bean.good.Comment;
import com.t1_network.taiyi.model.bean.good.CommentList;
import com.t1_network.taiyi.net.TYAPI;
import com.t1_network.taiyi.net.TYHttp;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/28 15:41
 * 修改记录:
 */
public class CommentAPI extends TYAPI {


    private CommentAPIListener listener;

    public CommentAPI(CommentAPIListener listener, String id, long limit) {
        this.listener = listener;

        addParams("id", id);
        addParams("pagenum", limit);
        addParams("pagesize", 10);


        new TYHttp(this).request();
    }

    @Override
    public void apiRequestError(long code, String msg) {
        listener.apiCommentFailure(code, msg);
    }

    @Override
    public void apiRequestSuccess(String data) {
        CommentList commentList = gson.fromJson(data, CommentList.class);
        listener.apiCommentSuccess(commentList.getCommentList());
    }

    @Override
    public String getURL() {
        return Build.HOST + "home/commentlist";
    }


    public interface CommentAPIListener {

        public void apiCommentSuccess(List<Comment> commentList);

        public void apiCommentFailure(long code, String msg);

    }


}

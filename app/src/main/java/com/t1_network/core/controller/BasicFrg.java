package com.t1_network.core.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.TipUtils;
import com.t1_network.taiyi.widget.TYProgressDialog;

import butterknife.ButterKnife;

/**
 * 功能：Fragment的父类
 * -------------------------------------------------------------------------------------------------
 * 创建者：樊辉达
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2015-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号   更新日期            更新人         更新内容
 */
public abstract class BasicFrg extends Fragment {

    public Context context;
    private int layoutId;
    public ImageLoader imageLoader;
    private View view;

    public TYProgressDialog dialog;

    public BasicFrg(int layoutId) {
        this.layoutId = layoutId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(layoutId, null);
        context = getContext();
        ButterKnife.bind(this, view);
        imageLoader = ImageLoader.getInstance();
        initView(view);
        dialog = TYProgressDialog.createDialog(context);

        return view;
    }

    public abstract void initView(View view);

    public void showTip(String msg) {
        KeyBoardUtils.hideKeyboard(context, view);
        TipUtils.snackBar(view, msg);
    }

    public void clearAdapter(BasicListAdapter adapter) {
        if (adapter != null) {
            adapter.getData().clear();
        }
    }
}

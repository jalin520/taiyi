package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.LogUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.db.TYSP;
import com.t1_network.taiyi.model.bean.home.HotWord;
import com.t1_network.taiyi.net.api.home.HotWordAPI;
import com.t1_network.taiyi.widget.FlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeSearchAct extends BasicAct implements HotWordAPI.HotWordAPIListener {

    public HomeSearchAct() {
        super(R.layout.act_home_search, NO_TITLE, false);
    }


    @Bind(R.id.act_search_edit_search)
    EditText editSearch;

    @Bind(R.id.act_search_layout_flow_tip_last_search)
    FlowLayout layoutLastTipSearch; //最近搜索的layout

    @Bind(R.id.act_search_layout_flow_tip)
    FlowLayout layoutTip;//re

    @Bind(R.id.act_home_search_layout_hot_search)
    LinearLayout layoutHostSearch; //热门搜索layout,包括标题

    @Bind(R.id.act_home_search_layout_last_search)
    LinearLayout layoutLastSearch; //热门搜索layout,包括标题


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeSearchAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        new HotWordAPI(this);
        initLastSearch();
    }


    private void initHotSearch(List<HotWord> hotSearchList) {


        if (hotSearchList.size() == 0) {
            layoutHostSearch.setVisibility(View.GONE);
            return;
        } else {
            layoutHostSearch.setVisibility(View.VISIBLE);
        }

        layoutTip.removeAllViews();

        for (int i = 0; i < hotSearchList.size(); i++) {

            HotWord hotWord = hotSearchList.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.item_hot_search_tip, null);

            TextView textName = (TextView) view.findViewById(R.id.item_hot_search_tip_text_name);
            textName.setText(hotWord.getWord());
            textName.setOnClickListener(new SearchTipClickListener(hotWord.getWord(), editSearch));

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            lp.topMargin = 5;
            lp.bottomMargin = 5;

            layoutTip.addView(view, lp);

        }

    }


    private void initLastSearch() {

        List<String> lastSearchList = TYSP.getLastSearch();

        if (lastSearchList.size() == 0) {
            layoutLastSearch.setVisibility(View.GONE);
            return;
        } else {
            layoutLastSearch.setVisibility(View.VISIBLE);
        }

        layoutLastTipSearch.removeAllViews();

        for (int i = 0; i < lastSearchList.size(); i++) {
            LogUtils.e("size" + lastSearchList.get(i));
            View view = LayoutInflater.from(this).inflate(R.layout.item_hot_search_tip, null);

            TextView textName = (TextView) view.findViewById(R.id.item_hot_search_tip_text_name);
            textName.setText(lastSearchList.get(i));
            view.setOnClickListener(new SearchTipClickListener(lastSearchList.get(i), editSearch));

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            lp.topMargin = 5;
            lp.bottomMargin = 5;

            layoutLastTipSearch.addView(view, lp);
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {

            String searchWord = editSearch.getText().toString();

            if ("".equals(searchWord.trim())) {
                showTip(ResUtils.getString(R.string.error_search_word_is_empty));
                return true;
            }


            TYSP.addLastSearch(searchWord);
            initLastSearch();
            GoodListAct.startActivity(HomeSearchAct.this, searchWord);
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    public class SearchTipClickListener implements View.OnClickListener {

        private String searchWord;

        private EditText editSearch;

        public SearchTipClickListener(String searchWord, EditText editSearch) {
            this.searchWord = searchWord;
            this.editSearch = editSearch;
        }

        @Override
        public void onClick(View v) {


            if ("".equals(searchWord.trim())) {
                showTip(ResUtils.getString(R.string.error_search_word_is_empty));
                return;
            }


            editSearch.setText(searchWord);
            TYSP.addLastSearch(searchWord);
            initLastSearch();
            GoodListAct.startActivity(HomeSearchAct.this, searchWord);
            finish();

        }
    }


    /**
     * 点击取消按钮
     *
     * @param view
     */
    @OnClick(R.id.act_search_tv_finish)
    public void cancelSearch(View view) {
        this.finish();
    }


    /**
     * 清除最近搜索记录
     */
    @OnClick(R.id.act_search_layout_clear_history)
    public void clearHistory() {
        TYSP.clearLastSearch();
        initLastSearch();
    }

    @Override
    public void apiHotWordFailure(long code, String msg) {
        showTip(msg);
    }

    @Override
    public void apiHotWordSuccess(List<HotWord> hotWordList) {
        initHotSearch(hotWordList);
    }
}



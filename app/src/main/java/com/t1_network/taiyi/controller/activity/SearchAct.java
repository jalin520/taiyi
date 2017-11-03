package com.t1_network.taiyi.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.controller.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchAct extends BasicAct {

    public SearchAct() {
        super(R.layout.act_search, NO_TITLE, false);
    }

    @Bind(R.id.act_good_list_search_rv)
    RecyclerView rv;


    @Bind(R.id.act_search_edit_search)
    EditText editSearch;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        List<String> hotSearchList = new ArrayList<String>();
        hotSearchList.add("家庭助理");
        hotSearchList.add("鱼跃");
        hotSearchList.add("三诺");
        hotSearchList.add("强生");
        hotSearchList.add("血压计");
        hotSearchList.add("血糖仪");
        hotSearchList.add("助听器");
        hotSearchList.add("儿童麻醉针");
//        initHotSearch(hotSearchList);
//        initLastSearch(hotSearchList);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        rv.setAdapter(new SearchAdapter(hotSearchList));
    }


//    @Bind(R.id.act_search_layout_flow_tip)
//    FlowLayout layoutTip;

//    private void initHotSearch(List<String> hotSearchList) {
//
//
//        for (int i = 0; i < hotSearchList.size(); i++) {
//            LogUtils.e("size" + hotSearchList.get(i));
//            View view = LayoutInflater.from(this).inflate(R.layout.item_hot_search_tip, null);
//
//            TextView textName = (TextView) view.findViewById(R.id.item_hot_search_tip_text_name);
//            textName.setText(hotSearchList.get(i));
//            textName.setOnClickListener(new SearchTipClickListener(hotSearchList.get(i), editSearch));
//
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.leftMargin = 5;
//            lp.rightMargin = 5;
//            lp.topMargin = 5;
//            lp.bottomMargin = 5;
//
//            layoutTip.addView(view, lp);
//
//        }
//
//    }

//    @Bind(R.id.act_search_layout_flow_tip_last_search)
//    FlowLayout layoutLastSearch;
//
//    private void initLastSearch(List<String> lastSearchList) {
//        for (int i = 0; i < lastSearchList.size(); i++) {
//            LogUtils.e("size" + lastSearchList.get(i));
//            View view = LayoutInflater.from(this).inflate(R.layout.item_hot_search_tip, null);
//
//            TextView textName = (TextView) view.findViewById(R.id.item_hot_search_tip_text_name);
//            textName.setText(lastSearchList.get(i));
//            view.setOnClickListener(new SearchTipClickListener(lastSearchList.get(i), editSearch));
//
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.leftMargin = 5;
//            lp.rightMargin = 5;
//            lp.topMargin = 5;
//            lp.bottomMargin = 5;
//
//            layoutLastSearch.addView(view, lp);
//        }
//    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {


            String searchWord = editSearch.getText().toString();
//            GoodListAct.startActivity(this, searchWord);


            // intent很重要，是两个Activity之间的纽带
            Intent in = new Intent();


            Bundle bundle = new Bundle();

            bundle.putString("searchWord", searchWord);

            in.putExtras(bundle);

            //上面代码和从a到b没有多大变化，关键不要再去startActivity了

            this.setResult(RESULT_OK, in);

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

            editSearch.setText(searchWord);
            GoodListAct.startActivity(SearchAct.this, searchWord);

        }
    }


    @OnClick(R.id.act_search_tv_finish)
    public void clickfinish(View view) {
        this.finish();
    }


}



package com.t1_network.taiyi.model.bean.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 15/12/25 10:30
 * 修改记录:
 */
public class HotWord {

    @SerializedName("name")
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public class HotWordList {
        @SerializedName("result")
        private List<HotWord> hotWordList;

        public List<HotWord> getHotWordList() {
            return hotWordList;
        }

        public void setHotWordList(List<HotWord> hotWordList) {
            this.hotWordList = hotWordList;
        }
    }

}

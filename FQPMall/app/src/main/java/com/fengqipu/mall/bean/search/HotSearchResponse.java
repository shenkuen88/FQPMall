package com.fengqipu.mall.bean.search;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/7/12 0012.
 */
public class HotSearchResponse extends BaseResponse{

    private List<String> wordsList;

    public List<String> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<String> wordsList) {
        this.wordsList = wordsList;
    }
}

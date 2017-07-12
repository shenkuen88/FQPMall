package com.fengqipu.mall.bean.search;

/**
 * Created by jwei on 2017/7/12 0012.
 */

public class HistorySearchBean {
    private int searchType;
    private String keyword;

    public HistorySearchBean() {
    }

    public HistorySearchBean(int searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

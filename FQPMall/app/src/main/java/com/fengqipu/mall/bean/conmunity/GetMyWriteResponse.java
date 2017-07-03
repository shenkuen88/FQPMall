package com.fengqipu.mall.bean.conmunity;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class GetMyWriteResponse extends BaseResponse {

    private int totalCount;

    private List<ArticleListBean> articleList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ArticleListBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleListBean> articleList) {
        this.articleList = articleList;
    }

}

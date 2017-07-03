package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class ProductSearchResponse extends BaseResponse
{


    private int totalCount;

    private List<ProductSearchBean> contentList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ProductSearchBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ProductSearchBean> contentList) {
        this.contentList = contentList;
    }

}

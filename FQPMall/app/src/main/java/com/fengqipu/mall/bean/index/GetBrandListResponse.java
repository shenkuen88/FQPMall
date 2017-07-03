package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class GetBrandListResponse extends BaseResponse
{


    private List<BrandListBean> brandList;

    public List<BrandListBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }

}

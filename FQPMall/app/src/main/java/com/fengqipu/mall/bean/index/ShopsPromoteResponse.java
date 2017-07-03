package com.fengqipu.mall.bean.index;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/6/23 0023.
 */

public class ShopsPromoteResponse extends BaseResponse {

    /**
     * shopList : [{"id":"1","type":null,"shopCode":null,"shopName":"测试企业","notice":null,"picUrl":"","advPicUrl1":"/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","advPicUrl2":"/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","advPicUrl3":"/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG","province":null,"city":null,"area":null,"addressDetail":null,"gpsLong":null,"gpsLati":null,"category1":null,"category2":null,"description":null,"phone":null,"businessLicenseFile":null,"legalPersonIDCardAFile":null,"legalPersonIDCardBFile":null,"legalPerson":null,"bank":null,"bankCode":null,"score":null,"viewCount":null,"delFlag":null,"status":null,"suspend":null,"maintainer":null,"createAdmin":null,"createTime":null,"advPicUrl1RequestUrl":"http://116.62.116.15/f/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","advPicUrl2RequestUrl":"http://116.62.116.15/f/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","advPicUrl3RequestUrl":"http://116.62.116.15/f/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG","advPicUrlList":["http://116.62.116.15/f/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","http://116.62.116.15/f/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","http://116.62.116.15/f/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG"],"picUrlRequestUrl":""},{"id":"4","type":null,"shopCode":null,"shopName":"南京XXX信息科技有限公司","notice":null,"picUrl":"/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG","advPicUrl1":"/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG","advPicUrl2":null,"advPicUrl3":null,"province":null,"city":null,"area":null,"addressDetail":null,"gpsLong":null,"gpsLati":null,"category1":null,"category2":null,"description":null,"phone":null,"businessLicenseFile":null,"legalPersonIDCardAFile":null,"legalPersonIDCardBFile":null,"legalPerson":null,"bank":null,"bankCode":null,"score":null,"viewCount":null,"delFlag":null,"status":null,"suspend":null,"maintainer":null,"createAdmin":null,"createTime":null,"advPicUrl1RequestUrl":"http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG","advPicUrl2RequestUrl":"","advPicUrl3RequestUrl":"","advPicUrlList":["http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG"],"picUrlRequestUrl":"http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG"}]
     * totalCount : 2
     */

    private int totalCount;
    private List<ShopListBean> shopList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ShopListBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopListBean> shopList) {
        this.shopList = shopList;
    }
}

package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class ColumnContentListResponse extends BaseResponse {


    /**
     * contentList : [{"contentID":"2","contentCode":null,"contentName":"内容2","keyword":null,"serviceType":1,"contentType":null,"abstracts":"摘要测试2","picUrl1":"http://221.226.118.110:18080/skin/upload/content/d92875ea-55ea-4064-afc9-3a364aa52971.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":" <p>1111<\/p>","specification":" <p>2222<\/p>","shopID":"1","categoryID":"2","categoryName":null,"subCategoryID":"3","subCategoryName":null,"brandID":null,"brandName":null,"free":1,"originalPrice":null,"unifiedPrice":null,"price":null,"score":null,"playUrl":null,"playCount":null,"appraiseCount":null,"adminID":"1","delFlag":0,"status":1,"createTime":"2016-07-19","shopName":"中巢","specificationLink":"http://221.226.118.110:18080/api/h5/detail?type=5&id=2","descriptionLink":"http://221.226.118.110:18080/api/h5/detail?type=4&id=2"},{"contentID":"3","contentCode":null,"contentName":"内容3","keyword":null,"serviceType":1,"contentType":null,"abstracts":"摘要测试3","picUrl1":"http://221.226.118.110:18080/skin/upload/content/0a433309-d5ec-441e-b3eb-ae413deb0beb.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":" <p>233333333333333<\/p>","specification":" <p>333333333333333333333333<\/p>","shopID":"1","categoryID":"2","categoryName":null,"subCategoryID":"3","subCategoryName":null,"brandID":null,"brandName":null,"free":1,"originalPrice":null,"unifiedPrice":null,"price":null,"score":null,"playUrl":null,"playCount":null,"appraiseCount":null,"adminID":"1","delFlag":0,"status":1,"createTime":"2016-07-19","shopName":"中巢","specificationLink":"http://221.226.118.110:18080/api/h5/detail?type=5&id=3","descriptionLink":"http://221.226.118.110:18080/api/h5/detail?type=4&id=3"},{"contentID":"1","contentCode":null,"contentName":"内容1","keyword":null,"serviceType":1,"contentType":null,"abstracts":"摘要测试1","picUrl1":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":" <p>1111<\/p>","specification":" <p>2222<\/p>","shopID":"1","categoryID":"2","categoryName":null,"subCategoryID":"3","subCategoryName":null,"brandID":null,"brandName":null,"free":1,"originalPrice":null,"unifiedPrice":null,"price":null,"score":null,"playUrl":null,"playCount":null,"appraiseCount":null,"adminID":"1","delFlag":0,"status":1,"createTime":"2016-07-18","shopName":"中巢","specificationLink":"http://221.226.118.110:18080/api/h5/detail?type=5&id=1","descriptionLink":"http://221.226.118.110:18080/api/h5/detail?type=4&id=1"}]
     * totalCount : 3
     */

    private int totalCount;
    /**
     * contentID : 2
     * contentCode : null
     * contentName : 内容2
     * keyword : null
     * serviceType : 1
     * contentType : null
     * abstracts : 摘要测试2
     * picUrl1 : http://221.226.118.110:18080/skin/upload/content/d92875ea-55ea-4064-afc9-3a364aa52971.jpg
     * picUrl2 :
     * picUrl3 :
     * picUrl4 :
     * description :  <p>1111</p>
     * specification :  <p>2222</p>
     * shopID : 1
     * categoryID : 2
     * categoryName : null
     * subCategoryID : 3
     * subCategoryName : null
     * brandID : null
     * brandName : null
     * free : 1
     * originalPrice : null
     * unifiedPrice : null
     * price : null
     * score : null
     * playUrl : null
     * playCount : null
     * appraiseCount : null
     * adminID : 1
     * delFlag : 0
     * status : 1
     * createTime : 2016-07-19
     * shopName : 中巢
     * specificationLink : http://221.226.118.110:18080/api/h5/detail?type=5&id=2
     * descriptionLink : http://221.226.118.110:18080/api/h5/detail?type=4&id=2
     */

    private List<ContentListBean> contentList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

}

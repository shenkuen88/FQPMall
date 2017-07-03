package com.fengqipu.mall.bean.video;

import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.index.ContentListBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */
public class SimilarResponse extends BaseResponse {

    /**
     * contentList : [{"contentID":"11","contentCode":null,"contentName":"内容11","keyword":null,"serviceType":1,"contentType":null,"abstracts":"分等第三方第三方","picUrl1":"http://221.226.118.110:18080/skin/upload/content/97751e35-9427-487b-bd5d-7d1d4fc9168a.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":" <p>敷敷<\/p>","specification":" ","shopID":"1","categoryID":"8","categoryName":null,"subCategoryID":"10","subCategoryName":null,"brandID":null,"brandName":null,"free":1,"originalPrice":null,"unifiedPrice":null,"price":null,"score":null,"playUrl":null,"playCount":null,"appraiseCount":null,"adminID":"1","delFlag":0,"status":1,"createTime":"2016-07-22","shopName":"中巢","descriptionLink":"http://221.226.118.110:18080/api/h5/detail?type=4&id=11","specificationLink":"http://221.226.118.110:18080/api/h5/detail?type=5&id=11"}]
     * totalCount : 1
     */

    private int totalCount;
    /**
     * contentID : 11
     * contentCode : null
     * contentName : 内容11
     * keyword : null
     * serviceType : 1
     * contentType : null
     * abstracts : 分等第三方第三方
     * picUrl1 : http://221.226.118.110:18080/skin/upload/content/97751e35-9427-487b-bd5d-7d1d4fc9168a.jpg
     * picUrl2 :
     * picUrl3 :
     * picUrl4 :
     * description :  <p>敷敷</p>
     * specification :
     * shopID : 1
     * categoryID : 8
     * categoryName : null
     * subCategoryID : 10
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
     * createTime : 2016-07-22
     * shopName : 中巢
     * descriptionLink : http://221.226.118.110:18080/api/h5/detail?type=4&id=11
     * specificationLink : http://221.226.118.110:18080/api/h5/detail?type=5&id=11
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

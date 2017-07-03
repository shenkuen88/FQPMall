package com.fengqipu.mall.bean.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwei on 2016/7/14 0014.
 */
public class RightBean {
    private String id;
    private String name;
    private String type;
    private List<SubRightBean> list=new ArrayList<SubRightBean>();

    public RightBean() {
    }

    public RightBean(String id, String name, String type, List<SubRightBean> list) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubRightBean> getList() {
        return list;
    }

    public void setList(List<SubRightBean> list) {
        this.list = list;
    }

    public static class SubRightBean {
        private String id;
        private String name;
        private String info;
        private String pic;
        private List<String> pics;

        public SubRightBean() {
        }

        public SubRightBean(String id, String name, String info, String pic, List<String> pics) {
            this.id = id;
            this.name = name;
            this.info = info;
            this.pic = pic;
            this.pics = pics;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}

package com.zhangwan.movieproject.app.bean;

import java.util.List;

/**
 * Created by zzg on 2018/4/13.
 */

public class FindBean {
    int total;
    List<ChannelBean> mychannel;//我的频道 我的频道
    List<FindSubBean> lists;//文章数据集

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChannelBean> getMychannel() {
        return mychannel;
    }

    public void setMychannel(List<ChannelBean> mychannel) {
        this.mychannel = mychannel;
    }

    public List<FindSubBean> getLists() {
        return lists;
    }

    public void setLists(List<FindSubBean> lists) {
        this.lists = lists;
    }

    public class FindSubBean{
        String  content;
        String  id;
        List<String> img;
        String  isad;
        String  origin;
        String  pic;
        String  readnum;
        String  title;
        String  url;
        String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getReadnum() {
            return readnum;
        }

        public void setReadnum(String readnum) {
            this.readnum = readnum;
        }

        public String getIsad() {
            return isad;
        }

        public void setIsad(String isad) {
            this.isad = isad;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}

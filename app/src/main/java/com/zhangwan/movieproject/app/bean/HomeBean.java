package com.zhangwan.movieproject.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class HomeBean implements Serializable {
    int            total;
    List<Platform> platform;
    List<Video>    video;
    List<Ad>       ad;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Platform> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Platform> platform) {
        this.platform = platform;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Ad> getAd() {
        return ad;
    }

    public void setAd(List<Ad> ad) {
        this.ad = ad;
    }

    //平台的
    public class Platform {
        String platform;
        String name;
        String pic;
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
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

    //广告
    public class Ad {
        String ad_type;
        String videoid;
        String shname;
        String url;
        String isvideo;
        String pic;

        public String getAd_type() {
            return ad_type;
        }

        public void setAd_type(String ad_type) {
            this.ad_type = ad_type;
        }

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
        }

        public String getShname() {
            return shname;
        }

        public void setShname(String shname) {
            this.shname = shname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsvideo() {
            return isvideo;
        }

        public void setIsvideo(String isvideo) {
            this.isvideo = isvideo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    //视屏
    public class Video {
        String isfirst;
        String videoid;
        String title;
        String pic;
        String desc;
        String isfirstpic;
        String url;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIsfirstpic() {
            return isfirstpic;
        }

        public void setIsfirstpic(String isfirstpic) {
            this.isfirstpic = isfirstpic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsfirst() {
            return isfirst;
        }

        public void setIsfirst(String isfirst) {
            this.isfirst = isfirst;
        }

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
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
    }

}

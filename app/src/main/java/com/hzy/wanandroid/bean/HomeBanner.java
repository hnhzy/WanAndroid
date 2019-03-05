package com.hzy.wanandroid.bean;

/**
 * Created by hzy on 2019/1/23
 **/
public class HomeBanner {

    private String desc;
    private int id;
    private String imagePath;
    private String url;
    private String title;
    private int isVisible;
    private int order;
    private int type;

    @Override
    public String toString() {
        return "BannerList{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", type=" + type +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.hzy.wanandroid.bean;

/**
 * Created by hzy on 2019/1/25
 **/
public class FreUsedWebBean {

    /**
     * icon :
     * id : 28
     * link : https://promotion.aliyun.com/ntms/act/ambassador/sharetouser.html?userCode=a9wfngm5
     * name : 阿里云优惠券
     * order : 999
     * visible : 1
     */

    private String icon;
    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}

package com.hzy.wanandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzy on 2019/1/25
 **/
public class NaviBean implements Serializable {

    private int cid;
    private String name;
    private List<NaviChildBean> articles;
    private Boolean isChecked = false;//用于表示是否选中

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NaviChildBean> getArticles() {
        return articles;
    }

    public void setArticles(List<NaviChildBean> articles) {
        this.articles = articles;
    }


}

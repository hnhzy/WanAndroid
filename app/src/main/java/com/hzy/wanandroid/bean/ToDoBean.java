package com.hzy.wanandroid.bean;

import java.io.Serializable;

/**
 * Created by hzy on 2019/3/6
 *
 * @author hzy
 */
public class ToDoBean implements Cloneable, Serializable {

    /**
     * completeDateStr :
     * content : 208189615220818961522081896152
     * date : 1551801600000
     * dateStr : 2019-03-06
     * id : 8290
     * priority : 0
     * status : 0
     * title : 5454546
     * type : 0
     * userId : 5990
     * completeDate : 1551715200000
     */
    private String where;
    private String completeDateStr;
    private String content;
    private long date;
    private String dateStr;
    private int id;
    private int priority;
    private int status;
    private String title;
    private int type;
    private int userId;
    private long completeDate;
    /**
     * 上半部分布局是否显示
     */
    private boolean isTopVisible;
    /**
     * 下面部分布局是否显示
     */
    private boolean isBottomVisible;
    /**
     * arrow是否朝上
     */
    private boolean isArrowUp;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public boolean isArrowUp() {
        return isArrowUp;
    }

    public void setArrowUp(boolean arrowUp) {
        isArrowUp = arrowUp;
    }

    public boolean isTopVisible() {
        return isTopVisible;
    }

    public void setTopVisible(boolean topVisible) {
        isTopVisible = topVisible;
    }

    public boolean isBottomVisible() {
        return isBottomVisible;
    }

    public void setBottomVisible(boolean bottomVisible) {
        isBottomVisible = bottomVisible;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

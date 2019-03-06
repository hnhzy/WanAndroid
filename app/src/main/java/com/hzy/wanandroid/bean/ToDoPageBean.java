package com.hzy.wanandroid.bean;

import java.util.List;

/**
 * Created by hzy on 2019/3/6
 **/
public class ToDoPageBean {


    /**
     * curPage : 1
     * datas : [{"completeDateStr":"","content":"208189615220818961522081896152",
     * "date":1551801600000,"dateStr":"2019-03-06","id":8290,"priority":0,"status":0,
     * "title":"5454546","type":0,"userId":5990},{"completeDateStr":"","content":"mUsername
     * .setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, &quot;请登录&quot;));",
     * "date":1551715200000,"dateStr":"2019-03-05","id":8261,"priority":0,"status":0,
     * "title":"ggahahfs","type":3,"userId":5990},{"completeDateStr":"","content":"mUsername
     * .setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, &quot;请登录&quot;));",
     * "date":1551715200000,"dateStr":"2019-03-05","id":8262,"priority":0,"status":0,
     * "title":"5454546","type":3,"userId":5990},{"completeDateStr":"","content":"mUsername
     * .setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, &quot;请登录&quot;));
     * mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, &quot;
     * 请登录&quot;));        mUsername.setText((String) SharedPreferencesUtil.getData(Constants
     * .USERNAME, &quot;请登录&quot;));","date":1551715200000,"dateStr":"2019-03-05","id":8263,
     * "priority":0,"status":0,"title":"ggahahfs","type":2,"userId":5990},{"completeDate
     * ":1551715200000,"completeDateStr":"2019-03-05","content":"mUsername.setText((String)
     * SharedPreferencesUtil.getData(Constants.USERNAME, &quot;请登录&quot;));",
     * "date":1551715200000,"dateStr":"2019-03-05","id":8264,"priority":0,"status":1,
     * "title":"mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME,
     * &quot;请登录&quot;));","type":1,"userId":5990},{"completeDateStr":"","content":"mUsername
     * .setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, &quot;请登录&quot;));",
     * "date":1551715200000,"dateStr":"2019-03-05","id":8265,"priority":0,"status":0,
     * "title":"mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME,
     * &quot;请登录&quot;));","type":0,"userId":5990}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 6
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ToDoBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ToDoBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ToDoBean> datas) {
        this.datas = datas;
    }


}

package com.hzy.wanandroid.service;


import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.FreUsedWebBean;
import com.hzy.wanandroid.bean.HomeBanner;
import com.hzy.wanandroid.bean.HotSearchBean;
import com.hzy.wanandroid.bean.KnowledgeSystem;
import com.hzy.wanandroid.bean.LoginBean;
import com.hzy.wanandroid.bean.NaviBean;
import com.hzy.wanandroid.bean.ProjectBean;
import com.hzy.wanandroid.bean.ProjectListBean;
import com.hzy.wanandroid.bean.PubAddrListBean;
import com.hzy.wanandroid.bean.PublicAddrBean;
import com.hzy.wanandroid.bean.SystemDataBean;
import com.hzy.wanandroid.http.ResponseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hzy on 2019/1/10
 * ApiService
 **/
public interface ApiService {

    //############################公众号############################################################

    /**
     * 获取公众号列表 Public address
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Observable<ResponseBean<List<PublicAddrBean>>> getPublicAddrList();

    /**
     * 查看某个公众号历史数据
     *
     * @return
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    Observable<ResponseBean<PubAddrListBean>> getPublicAddr(@Path("id") int id,
                                                            @Path("page") int page);


    /**
     * 在某个公众号中搜索历史文章
     *
     * @return
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    Observable<ResponseBean<PubAddrListBean>> getSearchPub(@Path("id") int id,
                                                           @Path("page") int page,
                                                           @Query("k") String k);


    //############################首页##############################################################

    /**
     * 1.1 首页文章列表
     *
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<ResponseBean<ArticleBean>> getArticle(@Path("page") int page);

    /**
     * 1.2 首页banner
     *
     * @return
     */
    @GET("/banner/json")
    Observable<ResponseBean<List<HomeBanner>>> getBannerList();

    /**
     * 1.3 常用网站  Frequently used websites
     *
     * @return
     */
    @GET("/friend/json")
    Observable<ResponseBean<List<FreUsedWebBean>>> getFreUsedWeb();

    /**
     * 1.4 搜索热词 Search hot words
     *
     * @return
     */
    @GET("/hotkey/json")
    Observable<ResponseBean<List<HotSearchBean>>> getHotSearch();


    //############################知识体系###########################################################

    /**
     * 2.1 体系数据
     *
     * @return
     */
    @GET("/tree/json")
    Observable<ResponseBean<List<SystemDataBean>>> getSystemData();

    /**
     * 2.2 知识体系下的文章
     *
     * @param cid
     * @param page
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<ResponseBean<KnowledgeSystem>> getsubSystem(@Path("page") int page, @Query(
            "cid") int cid);


    //############################导航##############################################################

    /**
     * 3.1 导航数据
     *
     * @return
     */
    @GET("/navi/json")
    Observable<ResponseBean<List<NaviBean>>> getNaviData();

    //############################项目##############################################################

    /**
     * 4.1 项目分类
     *
     * @return
     */
    @GET("/project/tree/json")
    Observable<ResponseBean<List<ProjectBean>>> getProjectSubject();

    /**
     * 4.2 项目列表数据
     *
     * @return
     */
    @GET("/project/list/{page}/json")
    Observable<ResponseBean<ProjectListBean>> getProjectList(@Path("page") int page, @Query(
            "cid") int cid);

    //############################5.登录与注册#####################################################

    /**
     * 5.1 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<ResponseBean<LoginBean>> postLogin(@Field("username") String username,
                                                  @Field("password") String password);


    /**
     * 5.2 注册
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<ResponseBean> postRegister(@Field("username") String username,
                                          @Field("password") String password,
                                          @Field("repassword") String repassword);

    /**
     * 5.3 退出
     *
     * @return
     */
    @GET("/user/logout/json")
    Observable<ResponseBean> getlogout();

    //############################6、收藏#####################################################


    /**
     * 6.1 收藏文章列表
     *
     * @param page
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<ResponseBean<ArticleBean>> getCollect(@Path("page") int page);


    /**
     * 6.2 收藏站内文章
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/collect/{id}/json")
    Observable<ResponseBean> insideCollect(@Path("id") int id);


    /**
     * 6.3 收藏站外文章
     *
     * @param title
     * @param author
     * @param link
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/collect/add/json")
    Observable<ResponseBean> outsideCollect(@Field("title") String title,
                                            @Field("author") String author,
                                            @Field("link") String link);

    /**
     * 6.4 取消收藏
     * 6.4.1 文章列表
     *
     * @param title
     * @param author
     * @param link
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<ResponseBean> articleListUncollect(@Path("id") int id,
                                                  @Field("title") String title,
                                                  @Field("author") String author,
                                                  @Field("link") String link);

    /**
     * 6.4 取消收藏
     * 6.4.2 我的收藏页面（该页面包含自己录入的内容）
     *
     * @param title
     * @param author
     * @param link
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    Observable<ResponseBean> myPageUncollect(@Path("id") int id,
                                             @Field("title") String title,
                                             @Field("author") String author,
                                             @Field("link") String link);


    /**
     * 6.5 收藏网站列表
     *
     * @return
     */
    @GET("/lg/collect/usertools/json")
    Observable<ResponseBean> collectionWebList();


    /**
     * 6.6 收藏网址
     *
     * @param name
     * @param link
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/collect/addtool/json")
    Observable<ResponseBean> collectionWeb(@Field("name") String name,
                                           @Field("link") String link);


    /**
     * 6.7 编辑收藏网站
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/collect/updatetool/json")
    Observable<ResponseBean> updateCollectionWeb(@Field("id") int id,
                                                 @Field("name") String name,
                                                 @Field("link") String link);

    /**
     * 6.8 删除收藏网站
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/lg/collect/deletetool/json")
    Observable<ResponseBean> deleteCollectionWeb(@Field("id") int id);


    //############################7、搜索#####################################################

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    Observable<ResponseBean<ArticleBean>> query(@Path("page") int page,
                                                @Field("k") String k);


}

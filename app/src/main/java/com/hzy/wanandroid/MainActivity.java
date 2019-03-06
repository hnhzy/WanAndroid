package com.hzy.wanandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.adapter.ViewPagerAdapter;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.fragment.home.HomeFragment;
import com.hzy.wanandroid.fragment.navi.NaviFragment;
import com.hzy.wanandroid.fragment.project.ProjectFragment;
import com.hzy.wanandroid.fragment.public_address.PublicAddrFragment;
import com.hzy.wanandroid.fragment.system.SystemFragment;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.ui.SupportWebActivity;
import com.hzy.wanandroid.ui.X5WebView;
import com.hzy.wanandroid.ui.article_search.ArticleSearchActivity;
import com.hzy.wanandroid.ui.freq_web.FreqWebActivity;
import com.hzy.wanandroid.ui.login.LoginActivity;
import com.hzy.wanandroid.ui.mycollect.MyCollectActivity;
import com.hzy.wanandroid.ui.search.SearchActivity;
import com.hzy.wanandroid.ui.todo.ToDoActivity;
import com.hzy.wanandroid.utils.ImageLoaderUtil;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBtNavi;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.vp_main)
    ViewPager mVpMain;

    TextView mUsername;
    ImageView mImvHead;

    ViewPagerAdapter mViewPagerAdapter;

    ActionBarDrawerToggle toggle;

    List<Fragment> fragments = new ArrayList<>(5);
    private String[] mTitleStrs = {"首页", "项目", "体系", "导航", "公众号"};


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fragments.add(HomeFragment.getInstance());
        fragments.add(ProjectFragment.getInstance());
        fragments.add(SystemFragment.getInstance());
        fragments.add(NaviFragment.getInstance());
        fragments.add(PublicAddrFragment.getInstance());

        mBtNavi.setItemIconTintList(null);//除去自带效果

        toolbar.inflateMenu(R.menu.menu_activity_main);
        setSupportActionBar(toolbar);


        toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mVpMain.setAdapter(mViewPagerAdapter);
        mVpMain.setOffscreenPageLimit(4);

        navigationView.setNavigationItemSelectedListener(this);
        mUsername = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        mImvHead = navigationView.getHeaderView(0).findViewById(R.id.imv_head);

        initListener();
    }


    private void initListener() {
        mBtNavi.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    naviTab(0);
                    break;
                case R.id.action_project:
                    naviTab(1);
                    break;
                case R.id.action_system:
                    naviTab(2);
                    break;
                case R.id.action_navi:
                    naviTab(3);
                    break;
                case R.id.action_pub:
                    naviTab(4);
                    break;
                default:
                    break;
            }
            return true;
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "onPageSelected:" + position);
                mBtNavi.getMenu().getItem(position).setChecked(true);
                toolbar.setTitle(mTitleStrs[position]);
                //写滑动页面后做的事，使每一个fragmen与一个page相对应
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, "请登录"));
    }

    /**
     * 点击Navigation切换Tab
     *
     * @param position tab下标
     */
    private void naviTab(int position) {
        mVpMain.setCurrentItem(position, true);
        toolbar.setTitle(mTitleStrs[position]);
    }


    @Override
    protected void initData() {
        mUsername.setText((String) SharedPreferencesUtil.getData(Constants.USERNAME, "请登录"));
        ImageLoaderUtil.LoadCircleImage(this, R.drawable.imv_head, mImvHead);
        if (!(Boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
            mUsername.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
            mImvHead.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        }
    }


    /**
     * onCreateOptionsMenu
     * 创建Menu
     *
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    protected void initSlidable() {
        // 禁止滑动返回
    }

    /**
     * onOptionsItemSelected
     * Menu内item的选定方法
     *
     * @param item item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Log.e("getCurrentItem", mVpMain.getCurrentItem() + "");
            if (mVpMain.getCurrentItem() == 4) {
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("id", PublicAddrFragment.getInstance().getAddrId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ArticleSearchActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onNavigationItemSelected
     * DrawerLayout内item的选定方法
     *
     * @param item item
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_collect:
                if ((Boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
                    startActivity(new Intent(this, MyCollectActivity.class));
                } else {
                    goLogin();
                }
                break;
            case R.id.nav_todo:
                if ((Boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
                    startActivity(new Intent(this, ToDoActivity.class));
                } else {
                    goLogin();
                }
                break;
            case R.id.nav_web:
                startActivity(new Intent(this, FreqWebActivity.class));
                break;
            case R.id.nav_tools:
                Intent intent = new Intent(this, X5WebView.class);
                intent.putExtra("mUrl", "http://www.wanandroid.com/tools");
                intent.putExtra("mTitle", "常用工具");
                startActivity(intent);
                break;
            case R.id.nav_support:
                startActivity(new Intent(this, SupportWebActivity.class));
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, "分享内容");
                startActivity(Intent.createChooser(shareIntent, "分享标题"));
                break;
            case R.id.nav_logout:
                if ((Boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
                    new AlertView("退出登录", "您确定退出登录？", null, new String[]{"取消", "确定"}, null, this,
                            AlertView.Style.Alert, (o, position) -> {
                        switch (position) {
                            case 0:

                                break;
                            case 1:
                                SharedPreferencesUtil.putData(Constants.ISLOGIN, false);
                                SharedPreferencesUtil.putData(Constants.USERNAME, "请登录");
                                App.apiService(ApiService.class)
                                        .getlogout()
                                        .compose(RxSchedulers.io_main())
                                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from
                                                (this)))
                                        .subscribe(bean -> {
                                            if (bean.getErrorCode() == 0) {
                                                ToastUtils.showShort("退出登录成功");
                                                mUsername.setText((String) SharedPreferencesUtil.getData
                                                        (Constants.USERNAME, "请登录"));
                                            }
                                        });
                                break;
                            default:
                                break;
                        }
                    }).show();
                } else {
                    goLogin();
                }

                break;
            default:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goLogin() {
        ToastUtils.showShort("请先登录");
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {//如果抽屉布局没有关闭则先关闭
            mDrawer.closeDrawer(GravityCompat.START);
        } else {//如果抽屉布局已经关闭则执行“再按一次退出程序”
            long currentTime = System.currentTimeMillis();
            if ((currentTime - exitTime) < 2000) {
                super.onBackPressed();
                System.exit(0);
            } else {
                Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
                exitTime = currentTime;
            }
        }
    }
}

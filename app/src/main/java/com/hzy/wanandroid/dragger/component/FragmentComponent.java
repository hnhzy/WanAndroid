package com.hzy.wanandroid.dragger.component;

import android.app.Activity;


import com.hzy.wanandroid.dragger.FragmentScope;
import com.hzy.wanandroid.dragger.module.FragmentModule;
import com.hzy.wanandroid.ui.activity.todo.donefragment.DoneFragment;
import com.hzy.wanandroid.ui.activity.todo.todofragment.ToDoFragment;
import com.hzy.wanandroid.ui.fragment.home.HomeFragment;
import com.hzy.wanandroid.ui.fragment.navi.NaviFragment;
import com.hzy.wanandroid.ui.fragment.project.profragment.ProjectFragment;
import com.hzy.wanandroid.ui.fragment.project.projectlist.ProjectListFragment;
import com.hzy.wanandroid.ui.fragment.system.sysfragment.SystemFragment;
import com.hzy.wanandroid.ui.fragment.system.systemlist.SubSystemFragment;
import com.hzy.wanandroid.ui.fragment.wxpulic.address.PublicAddrFragment;
import com.hzy.wanandroid.ui.fragment.wxpulic.addrlist.PubListFragment;

import dagger.Component;

/**
 * @author Administrator
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(ProjectFragment projectFragment);

    void inject(SystemFragment systemFragment);

    void inject(NaviFragment naviFragment);

    void inject(PublicAddrFragment publicAddrFragment);

    void inject(PubListFragment pubListFragment);

    void inject(ProjectListFragment projectListFragment);

    void inject(SubSystemFragment subSystemFragment);

    void inject(ToDoFragment toDoFragment);

    void inject(DoneFragment doneFragment);
}

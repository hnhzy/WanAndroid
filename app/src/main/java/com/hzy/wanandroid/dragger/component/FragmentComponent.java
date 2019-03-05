package com.hzy.wanandroid.dragger.component;

import android.app.Activity;


import com.hzy.wanandroid.dragger.FragmentScope;
import com.hzy.wanandroid.dragger.module.FragmentModule;
import com.hzy.wanandroid.fragment.PubList.PubListFragment;
import com.hzy.wanandroid.fragment.home.HomeFragment;
import com.hzy.wanandroid.fragment.navi.NaviFragment;
import com.hzy.wanandroid.fragment.project.ProjectFragment;
import com.hzy.wanandroid.fragment.project_list.ProjectListFragment;
import com.hzy.wanandroid.fragment.public_address.PublicAddrFragment;
import com.hzy.wanandroid.fragment.subsystem.SubSystemFragment;
import com.hzy.wanandroid.fragment.system.SystemFragment;

import dagger.Component;

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
}

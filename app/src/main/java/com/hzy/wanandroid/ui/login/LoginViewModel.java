package com.hzy.wanandroid.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.bean.LoginBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;

import io.reactivex.observers.DefaultObserver;

/**
 * @author yinbiao
 * @date 2019/3/7
 */
public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    // 创建LiveData
    private MutableLiveData<ResponseBean<LoginBean>> result = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //页面销毁时调用
        Log.d(TAG, "onCleared: ");
    }

    public void postLogin(String username, String password) {
        App.apiService(ApiService.class)
                .postLogin(username, password)
                .compose(RxSchedulers.io_main())
                .subscribe(new DefaultObserver<ResponseBean<LoginBean>>() {
                    @Override
                    public void onNext(ResponseBean<LoginBean> responseBean) {
                        result.setValue(responseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public MutableLiveData<ResponseBean<LoginBean>> loginResult() {
        return result;
    }
}

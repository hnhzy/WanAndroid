package com.hzy.wanandroid.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hzy on 2019/1/10
 * 线程调度
 **/
public class RxSchedulers {

    final static ObservableTransformer Stf = upstream -> upstream.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());

    static <T> ObservableTransformer<T, T> applySchedulers() {
        return Stf;
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> io_main() {
        return (ObservableTransformer<T, T>) applySchedulers();
    }

}

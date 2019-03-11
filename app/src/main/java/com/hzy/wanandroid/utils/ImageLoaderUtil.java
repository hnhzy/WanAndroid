package com.hzy.wanandroid.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * Created by hzy on 2019/1/10
 * 图片加载
 *
 * @author Administrator
 */
public class ImageLoaderUtil {

    /**
     * 圆形头像
     */
    public static void LoadCircleImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.centerCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(RequestOptions.circleCropTransform())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 常规使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 自定义RequestOptions使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView,
                                 RequestOptions requestOptions) {
        Glide.with(context).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 自定义RequestOptions使用
     */
    public static void LoadImage(Fragment fragment, Object url, ImageView imageView,
                                 RequestOptions requestOptions) {
        Glide.with(fragment).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageViewTarget imageViewTarget) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);
    }

    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView,
                                 RequestListener listener) {
        Glide.with(context).load(url)
                //.thumbnail(0.1f)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .listener(listener)
                .into(imageView);
    }


}

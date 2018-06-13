package com.gxtc.commlibrary.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.gxtc.commlibrary.R;
import com.gxtc.commlibrary.other.GlideCircularTransform;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * 图片加载框架
 */
public class ImageHelper {
    public static int TRANSTION_TIME = 300;

    public static void loadImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("imageView 不能为null");
        }
        RequestOptions options = new RequestOptions().dontAnimate().error(R.drawable.live_foreshow_img_temp);
        Glide.with(context).load(url).transition(withCrossFade(TRANSTION_TIME)).apply(options).into(imageView);
    }

    //加载无默认图片
    public void LoadNoDefaultImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    //加载RoundedImageView
    public void loadRoundedImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions().dontAnimate().error(R.drawable.error_image).transforms(new GlideCircularTransform(context));
        Glide.with(context).load(url).transition(withCrossFade(TRANSTION_TIME)).apply(options).into(imageView);
    }


    //加载头像RoundedImageView从int
    public void loadHeadRoundedInt(Context context, int drawle, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions().dontAnimate().error(R.drawable.error_image).transforms(new GlideCircularTransform(context));
        Glide.with(context).load(drawle).transition(withCrossFade(TRANSTION_TIME)).apply(options).into(imageView);
    }


    //加载头像RoundedImageView从文件
    public void loadHeadRoundedImageFile(Context context, File file, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions().dontAnimate().error(R.drawable.error_image).transforms(new GlideCircularTransform(context));
        Glide.with(context).load(file).transition(withCrossFade(TRANSTION_TIME)).apply(options).into(imageView);
    }

    //加载banner图片
    public void loadBannerImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions().dontAnimate().placeholder(R.drawable.default_image_banner).error(R.drawable.live_foreshow_img_temp);
        Glide.with(context).load(url).transition(withCrossFade(TRANSTION_TIME)).apply(options).into(imageView);
    }

}

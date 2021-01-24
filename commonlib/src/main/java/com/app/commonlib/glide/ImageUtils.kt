package com.app.commonlib.glide

import android.widget.ImageView
import com.app.commonlib.Common
import com.app.commonlib.utils.DisplayUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * @ClassName ImageUtils
 * @Author DYJ
 * @Date 2021/1/24 19:48
 * @Version 1.0
 * @Description TODO
 */

/**
 * 加载一般图片
 */
fun ImageView.load(url: Any) =
        Glide.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(Common.ERROR)
                .placeholder(Common.PLACEHOLDER)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .dontAnimate()
                .into(this)

/**
 * 加载圆角图片
 */
fun ImageView.loadRound(url: Any, radius: Int) {
    val options =
            RequestOptions.bitmapTransform(CenterCropRoundCornerTransform(radius))
    Glide.with(this.context)
            .load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .dontAnimate()
            .into(this)
}

/**
 * 加载圆角图片 某一方位
 */
fun ImageView.loadRound(
        url: Any,
        dpValue: Float,
        leftTop: Boolean = true,
        rightTop: Boolean = true,
        leftBottom: Boolean = true,
        rightBottom: Boolean = true
) {
    val transform =
            RoundedCornersTransform(context, DisplayUtils.dip2px(context, dpValue).toFloat())
    transform.setNeedCorner(leftTop, rightTop, leftBottom, rightBottom)
    val options: RequestOptions =
            RequestOptions().placeholder(android.R.color.holo_red_light).transform(transform)
    Glide.with(this.context)
            .load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .dontAnimate()
            .into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCenter(url: Any) =
        Glide.with(context!!)
                .load(url)
                .placeholder(Common.PLACEHOLDER)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(Common.ERROR)
                .transform(CircleCrop())
                .into(this)

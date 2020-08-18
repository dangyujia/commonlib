package com.app.commonlib

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.app.commonlib.Commonlib.ERROR
import com.app.commonlib.Commonlib.PLACEHOLDER
import com.app.commonlib.glide.CenterCropRoundCornerTransform
import com.app.commonlib.utils.DisplayUtils
import com.app.rain.commonlib.util.glide.RoundedCornersTransform
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * 加载一般图片
 */
fun ImageView.load(url: Any) =
        Glide.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(ERROR)
                .placeholder(PLACEHOLDER)
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
                .placeholder(PLACEHOLDER)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(ERROR)
                .transform(CircleCrop())
                .into(this)


fun post(block: () -> Unit) {
    postDelay(block, 0)
}

val MAIN_HANDLER = Handler(Looper.getMainLooper())

fun postDelay(block: () -> Unit, delay: Long) {
    MAIN_HANDLER.postDelayed({ block() }, delay)
}

/**
 * 获取屏幕宽度
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

package com.app.commonlib.glide

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

/**
 * @ClassName CenterCropRoundCornerTransform
 * @Author DYJ
 * @Date 2020/7/21 21:54
 * @Version 1.0
 * @Description glide 圆形
 */
open class CenterCropRoundCornerTransform(px: Int) : CenterCrop() {
    private var radius = 0f

    init {
        this.radius = px.toFloat()
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap =
            TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        return roundCrop(pool,bitmap)!!
    }
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        super.updateDiskCacheKey(messageDigest)
    }

    open fun roundCrop(
        pool: BitmapPool,
        source: Bitmap?
    ): Bitmap? {
        if (source == null) return null
        var result: Bitmap? = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(
                source.width,
                source.height,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(
                source,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
        )
        paint.isAntiAlias = true
        val rectF = RectF(
            0f, 0f,
            source.width.toFloat(),
            source.height.toFloat()
        )
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }

    open fun getId(): String? {
        return javaClass.name + Math.round(radius.toFloat())
    }
}
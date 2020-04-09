package com.example.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.common.R

import jp.wasabeef.glide.transformations.BlurTransformation

import java.io.File

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 显示相关工具类
 */
object DisplayUtils {
    /**
     * 将px值转换为dp值
     */
    fun px2dp(pxValue: Float): Int {
        val scale = AppUtils.context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dp值转换为px值
     */
    fun dp2px(dpValue: Float): Int {
        val scale = AppUtils.context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值
     */
    fun px2sp(pxValue: Float): Int {
        val scale = AppUtils.context.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值
     */
    fun sp2px(dpValue: Float): Int {
        val scale = AppUtils.context.resources.displayMetrics.scaledDensity
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidthPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeightPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 将一个view转换成bitmap位图
     *
     * @param view 要转换的View
     * @return view转换的bitmap
     */
    fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        view.draw(Canvas(bitmap))
        return bitmap
    }

    /**
     * 获取模糊虚化的bitmap
     *
     * @param context
     * @param bitmap  要模糊的图片
     * @param radius  模糊等级 >=0 && <=25
     * @return
     */
    fun getBlurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurBitmap(context, bitmap, radius)
        } else bitmap
    }

    /**
     * android系统的模糊方法
     *
     * @param bitmap 要模糊的图片
     * @param radius 模糊等级 >=0 && <=25
     */
    fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            val outBitmap = Bitmap.createBitmap(
                bitmap.width, bitmap.height, Bitmap
                    .Config.ARGB_8888
            )
            //Instantiate a new Renderscript
            val rs = RenderScript.create(context)
            //Create an Intrinsic Blur Script using the Renderscript
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            val allIn = Allocation.createFromBitmap(rs, bitmap)
            val allOut = Allocation.createFromBitmap(rs, outBitmap)
            //Set the radius of the blur
            blurScript.setRadius(radius.toFloat())
            //Perform the Renderscript
            blurScript.setInput(allIn)
            blurScript.forEach(allOut)
            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap)
            //recycle the original bitmap
            bitmap.recycle()
            //After finishing everything, we destroy the Renderscript.
            rs.destroy()
            return outBitmap
        } else {
            return bitmap
        }
    }

    /**
     * 显示网络虚化图片
     *
     * @param context   context
     * @param imgUrl    图片url
     * @param imageView 要显示的imageview
     */
    fun displayBlurImg(context: Context, imgUrl: String, imageView: ImageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊

        Glide.with(context)
            .load(imgUrl)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .error(R.drawable.stackblur_default)
            .placeholder(R.drawable.stackblur_default)
            .into(imageView)
//            .crossFade(300)

//            .bitmapTransform(BlurTransformation(context, 23, 4))
//            .into(imageView)

    }

    /**
     * 显示本地虚化图片
     *
     * @param context   context
     * @param file      本地图片file
     * @param imageView 要显示的imageview
     */
    fun displayBlurImg(context: Context, file: File, imageView: ImageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊

        val bitmapTransform = RequestOptions.bitmapTransform(BlurTransformation(23, 4))
        Glide.with(context)
            .load(file)
            .error(R.drawable.stackblur_default)
            .placeholder(R.drawable.stackblur_default)
//            .crossFade(300)
            .apply(bitmapTransform)
//            .bitmapTransform(BlurTransformation(context, 23, 4))
            .into(imageView)
    }

    /**
     * 显示资源虚化图片
     *
     * @param context    context
     * @param resourceId 图片资源id
     * @param imageView  要显示的imageview
     */
    fun displayBlurImg(context: Context, resourceId: Int?, imageView: ImageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        val bitmapTransform = RequestOptions.bitmapTransform(BlurTransformation(23, 4))
        Glide.with(context)
            .load(resourceId)
            .error(R.drawable.stackblur_default)
            .placeholder(R.drawable.stackblur_default)
//            .crossFade(300)
            .apply(bitmapTransform)
            .into(imageView)
    }
}

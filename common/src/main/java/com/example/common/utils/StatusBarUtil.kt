package com.example.common.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowManager

object StatusBarUtil {
    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColor(activity: Activity, color: Int) {
        setBarColor(activity, color)
    }

    /**
     * 设置状态栏背景色
     * 4.4以下不处理
     * 4.4使用默认沉浸式状态栏
     *
     * @param color 要为状态栏设置的颜色值
     */
    private fun setBarColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            val decorView = window.decorView
            //沉浸式状态栏(4.4-5.0透明，5 .0以上半透明)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //android5.0以上设置透明效果
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                //清除flag，为了android5.0以上也全透明效果
                //让应用的主体内容占用系统状态栏的空间
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = decorView.systemUiVisibility or option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color//设置状态栏背景色

            }
        }
    }


    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setColor(activity, Color.TRANSPARENT)
    }

    /**
     * 获取导航栏的高度
     *
     * @return
     */
    fun getStatusBarHeight(): Int {
        val resources = AppUtils.context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}
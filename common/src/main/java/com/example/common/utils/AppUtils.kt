package com.example.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context

import android.telephony.TelephonyManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.common.BaseApplication


object AppUtils {

    val context: Context
        get() = BaseApplication.context

    val handler: android.os.Handler?
        get() = BaseApplication.handler

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
    val isRunOnUIThread: Boolean
        get() {
            val myTid = android.os.Process.myTid()
            return if (myTid == BaseApplication.mainThreadId) {
                true
            } else false
        }


    /**
     * 获取版本名称
     */
    fun getAppVersionName(context: Context): String? {
        var versionName: String? = ""
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            if (versionName == null || versionName.length <= 0) {
                return ""
            }
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }

        return versionName
    }

    /**
     * 获取版本号
     */
    fun getAppVersionCode(context: Context): Int {
        var versioncode = -1
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versioncode = pi.versionCode
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }

        return versioncode
    }

    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String {
        val tm = context.getSystemService(
            Context
                .TELEPHONY_SERVICE
        ) as TelephonyManager
        return tm.deviceId
    }

    /**
     * 显示软键盘
     */
    fun openSoftInput(et: EditText) {
        val inputMethodManager = et.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftInput(et: EditText) {
        val inputMethodManager = et.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            et.windowToken, InputMethodManager
                .HIDE_NOT_ALWAYS
        )
    }

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     * @return 是否隐藏
     */
    fun hideKeyboard(
        event: MotionEvent, view: View?,
        activity: Activity
    ) {
        try {
            if (view != null && view is EditText) {
                val location = intArrayOf(0, 0)
                view.getLocationInWindow(location)
                val left = location[0]
                val top = location[1]
                val right = left + view.width
                val bootom = top + view.height
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.rawX < left || event.rawX > right
                    || event.y < top || event.rawY > bootom
                ) {
                    // 隐藏键盘
                    val token = view.windowToken
                    val inputMethodManager = activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        token,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    fun runOnUIThread(r: Runnable) {

        if (isRunOnUIThread) {
            // 已经是主线程, 直接运行
            r.run()
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            BaseApplication.handler?.post(r)
        }
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}

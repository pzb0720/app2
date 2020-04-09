package com.example.common.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    private var mToast: Toast? = null

    /**
     * 显示一个toast提示
     *
     * @param resourceId toast字符串资源id
     */
    fun showToast(resourceId: Int) {
        showToast(ResourcesUtils.getString(resourceId))
    }

    /**
     * 显示一个toast提示
     *
     * @param msg
     */
    fun showToast(msg: String) {
        showToast(msg, Toast.LENGTH_LONG)
    }

    /**
     * 显示一个toast提示
     *
     * @param msg
     * @param lengthLong
     */
    fun showToast(msg: String, lengthLong: Int) {
        showToast(AppUtils.context, msg, lengthLong)
    }

    /**
     * @param mContext
     * @param msg
     * @param lengthLong
     */
    fun showToast(mContext: Context, msg: String, lengthLong: Int) {
        AppUtils.runOnUIThread(Runnable {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, msg, lengthLong)
            } else {
                mToast!!.setText(msg)
                mToast!!.setDuration(lengthLong)
            }
            mToast!!.show()
        })
    }

}
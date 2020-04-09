package com.example.common

import android.app.Application
import android.content.Context
import android.os.Handler

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        handler = Handler()
        mainThreadId = android.os.Process.myTid()
    }

    companion object {

        /**
         * 获取上下文对象
         *
         * @return context
         */
        lateinit var context: Context
            private set
        /**
         * 获取全局handler
         *
         * @return 全局handler
         */
        var handler: Handler? = null
            private set
        /**
         * 获取主线程id
         *
         * @return 主线程id
         */
        var mainThreadId: Int? = 0
            private set
    }
}

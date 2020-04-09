package com.example.common.utils

object ResourcesUtils {
    fun getString(resourceId: Int): String {
        return AppUtils.context.resources.getString(resourceId)
    }

}
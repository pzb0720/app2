package com.example.common.utils

import java.util.regex.Pattern

object StringUtils {
    /**
     * 过滤特殊字符(\/:*?"<>|)
     */
    fun stringFilter(str: String?): String? {
        if (str == null) {
            return null
        }
        val regEx = "<[^>]+>"
        //        String regEx = "[\\/:*?\"<>|]";
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.replaceAll("").trim { it <= ' ' }
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    fun isEmpty(value: String?): Boolean {
        return !(value != null && !"".equals(value.trim { it <= ' ' }, ignoreCase = true)
                && !"null".equals(value.trim { it <= ' ' }, ignoreCase = true))
    }
}
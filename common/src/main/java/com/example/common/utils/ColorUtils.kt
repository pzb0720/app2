package com.example.common.utils

import android.content.Context
import android.util.TypedValue
import com.example.common.R

object ColorUtils{
     fun getColorPrimary(context :Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }
}

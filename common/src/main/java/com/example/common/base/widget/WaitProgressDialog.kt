package com.example.mylibrary.base.widget

import android.app.ProgressDialog
import android.content.Context
import android.os.Parcel
import android.os.Parcelable

class WaitProgressDialog(context: Context?, theme: Int) : ProgressDialog(context, theme) {
    init {
        setCancelable(false)
    }
}
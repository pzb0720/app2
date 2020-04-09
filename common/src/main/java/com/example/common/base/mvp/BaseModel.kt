package com.example.mylibrary.base.mvp

import io.reactivex.disposables.CompositeDisposable

open class BaseModel {
    val mDisposablePool: CompositeDisposable by lazy { CompositeDisposable() }
}

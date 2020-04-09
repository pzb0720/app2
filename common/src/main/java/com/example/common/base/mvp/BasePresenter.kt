package com.example.mylibrary.base.mvp

open class BasePresenter<V : ITopView> {
    var mView: V? = null
}
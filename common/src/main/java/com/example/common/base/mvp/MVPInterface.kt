package com.example.mylibrary.base.mvp

import android.app.Activity
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.annotations.NotNull

interface ITopView : LifecycleOwner {
        fun getCxt(): Context?
    fun inited()

    fun finish(resultCode: Int = Activity.RESULT_CANCELED)
    fun showLoading(@NotNull msg: String)
    fun showLoading(@StringRes srtResId: Int)
    fun dismissLoading()
    fun showToast(@StringRes srtResId: Int)
    fun showToast(@NotNull message: String)


}

interface ITopPresenter : LifecycleObserver {
    fun attachView(view: ITopView)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachView()
}

interface ITopModel {
    fun onDetach()
}

interface IView<P : ITopPresenter> : ITopView {
    var mPresenter: P
    override fun inited() {
        mPresenter.attachView(this)
    }
}

interface IPresenter<V : ITopView, M : IModel> : ITopPresenter {
    var mView: V?
    var mModel: M?
    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: ITopView) {
        mView = view as V
        mView?.lifecycle?.addObserver(this)

    }

    override fun detachView() {
        mModel?.onDetach()
        mModel = null
        mView = null
    }

    private val isViewAttached: Boolean
        get() = mView != null

    private class MvpViewNotAttachedException internal constructor() : RuntimeException(
        "Please call IPresenter.attachView(IBaseView) before"
                + " requesting data to the IPresenter"
    )
}

interface IModel : ITopModel {
    val mDisposablePool: CompositeDisposable

    fun addDisposable(disposable: Disposable) {
        mDisposablePool.add(disposable)
    }

    override fun onDetach() {
        Logger.e("model detach")
        if (!mDisposablePool.isDisposed) {
            mDisposablePool.clear()
        }
    }
}



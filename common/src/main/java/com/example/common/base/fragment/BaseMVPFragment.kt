package com.example.mylibrary.base.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import com.example.common.base.activity.BaseActivity
import com.example.common.utils.ToastUtils
import com.example.mylibrary.base.mvp.ITopPresenter
import com.example.mylibrary.base.mvp.ITopView
import com.example.mylibrary.base.mvp.IView
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseMVPFragment<V : ITopView, P : ITopPresenter> : BaseFragment(), IView<P> {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inited()
    }

    override fun getCxt(): Context? = mContext
    override fun finish(resultCode: Int) {

    }

    override fun showLoading(msg: String) {
        showProgressDialog(msg)
    }

    override fun showLoading(srtResId: Int) {
        showProgressDialog(getString(srtResId))
    }

    override fun dismissLoading() {
        hideProgressDialog()
    }

    override fun showToast(srtResId: Int) {
        ToastUtils.showToast(srtResId)
    }

    override fun showToast(message: String) {
        ToastUtils.showToast(message)
    }


    fun popToFragment(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        popTo(targetFragmentClass, includeTargetFragment)
    }

    fun startNewFragment(@NonNull supportFragment: SupportFragment) {
        start(supportFragment)
    }

    fun startNewFragmentWithPop(@NonNull supportFragment: SupportFragment) {
        start(supportFragment)
    }

    fun startNewFragmentForResult(@NonNull supportFragment: SupportFragment, requestCode: Int) {
        start(supportFragment, requestCode)
    }

    fun setOnFragmentResult(resultCode: Int, data: Bundle) {
        setFragmentResult(resultCode, data)
    }

    fun startNewActivity(@NonNull clz: Class<*>) {
        (mActivity as BaseActivity).startActivity(clz)
    }

    fun startNewActivity(@NonNull clz: Class<*>, bundle: Bundle) {
        (mActivity as BaseActivity).startActivity(clz, bundle)
    }

    fun startNewActivityForResult(@NonNull clz: Class<*>, bundle: Bundle, requestCode: Int) {
        (mActivity as BaseActivity).startActivityForResult(clz, bundle, requestCode)
    }
}